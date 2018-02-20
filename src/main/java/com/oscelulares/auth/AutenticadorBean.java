/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oscelulares.auth;
import com.oscelulares.jpa.UserJpaController;
import com.oscelulares.model.User;
import com.oscelulares.util.JSFUtil;
import com.oscelulares.util.Senhas;
import com.oscelulares.util.SessionUtil;
import java.io.Serializable;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

/**
 *
 * @author luciano
 */

@RequestScoped
@ManagedBean(name = "autenticadorBean")
public class AutenticadorBean implements Serializable{
    private static final long serialVersionUID = 1L;
        private User usuario;
	private String nome;
	private String senha;
        private EntityManager em = null;
        private InitialContext ic = null;
        @PersistenceUnit(unitName="com_oscelulares_war_1.0-SNAPSHOTPU") //inject from your application server
        EntityManagerFactory emf;
        @Resource //inject from your application server
        UserTransaction utx; 
        
        
        @PostConstruct
        public void preparaLogin(){
            try {
                //instancia Users para busca no banco
                usuario = new User();
                //seta campos digitados
                usuario.setLogin(getNome());
                usuario.setPasswd(Senhas.encript(getSenha()));
                //usuario.setPasswd(getSenha()); // senha sem encriptografar
                
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

	public String autentica()throws SQLException{
            System.out.println("Autentica Login.");
                     
            try{      
                preparaLogin();
                               
                UserJpaController jpa = new UserJpaController(utx, emf);
                em = emf.createEntityManager();
                
                
                //usuario = jpa.login(usuarios,em);
                User resp = jpa.login(usuario,em);

                if(usuario.getLogin()== ""){
                    JSFUtil.adicionarMensagemErro("Usuário ou senha Incorretos.");
                    return null;
                }else{
                    setSenha(Senhas.encript(getSenha()));
                    
                    if(usuario.getLogin().equals(resp.getLogin()) && usuario.getPasswd().equals(resp.getPasswd())){
                        System.out.println("Login confirmado (usuario e senha).");		

                        //ADD USUARIO NA SESSION
                        Object b = new Object();
                        SessionUtil.setParam("USUARIOLogado", b);
                        
                        //Registra Entrada
                        usuario = resp;
                        registraLogin(usuario);
                        
                        //
                        SessionBean sess = new SessionBean();
                        sess.start(usuario);

                        //pagina principal
                        return "/index.xhtml?faces-redirect=true";
                    }else{
                        System.out.println("Usuário ou senha Incorretos.");
                        JSFUtil.adicionarMensagemErro("Usuário ou senha Incorretos.");
                        return null;
                    }
                }
            }catch(SQLException e){
                e.printStackTrace();
                return null;
            }


	}
     
        public void registraLogin(User u)throws SQLException{
            SessionUtil session = new SessionUtil();
            System.out.println("----------------------------------------------");
            System.out.println("sessao "+session.getSessao());
            System.out.println(session.getIP());
            System.out.println(session.getDataServer());
            System.out.println(session.getTimeServer());
            System.out.println("----------------------------------------------");
            
            //Pega sessao, ip e ultimologin
            //usuarios.setOnline(true);
            usuario.setSession(session.getSessao());
            //usuarios.setIp(session.getIP());
            //usuarios.setUltimologin(session.getUltimoLogin());
            
            

            UserJpaController jpa = new UserJpaController(utx,emf);
            
            jpa.registerLogin(usuario);
            
        }   
        
        public String registraLogout()throws SQLException{            
            SessionUtil session = new SessionUtil();
            User usr = new User();
            
            //seta sessao para busca
            usr.setSession(session.getSessao());
            

            UserJpaController jpa = new UserJpaController(utx,emf);
            
            //faz a busca por sessao
            em = emf.createEntityManager();
            usuario = jpa.findUsersForSessao(usr,em); 
            //System.out.println("resultado busca "+usuarios.getSessao());
            
            //REMOVER USUARIO DA SESSION
            session.invalidate();
            //if(SessionUtil.getSession() == emf){
            if(SessionUtil.getSession() == null){
                System.out.println("----------------------------------------------");
                System.out.println("Sessao finalizada.");
                System.out.println("----------------------------------------------");
                
                               
                //usuarios.setIp("");
                //usuarios.setOnline(false);
                usuario.setSession("");
                //usuarios.setTelaatual("");
                jpa.registerLogout(usuario);

                
            }else{
                System.out.println("----------------------------------------------");
                System.out.println("Impossível finalizar sessao. ");
                System.out.println("----------------------------------------------");
            }
            return "/login.xhtml?faces-redirect=true";
            
	}
        

	public String getSenha() {
		return senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

    public User getUsers() {
        return usuario;
    }

    public void setUsers(User usuarios) {
        this.usuario = usuarios;
    }
    
}
