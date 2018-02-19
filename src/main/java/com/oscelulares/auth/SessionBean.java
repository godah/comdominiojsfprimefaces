package com.oscelulares.auth;
import com.oscelulares.jpa.UserJpaController;
import com.oscelulares.model.User;
import com.oscelulares.util.SessionUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

@ManagedBean(name = "MBSession")
@SessionScoped
public class SessionBean implements Serializable{
    private User usuario;
    //private String data; //para conversao de formato de data
    private EntityManager em = null;
    private InitialContext ic = null;
    @PersistenceUnit(unitName="com_oscelulares_war_1.0-SNAPSHOTPU") //inject from your application server
    EntityManagerFactory emf;
    @Resource //inject from your application server
    UserTransaction utx; 
    
    public void start(User u){
         usuario = u;
    }

    /**
     * @return the usuario
     */
    public User getUsuarios() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuarios(User usuario) {
        this.usuario = usuario;
    }
    
    @PostConstruct
    public void buscaUsuario(){
        SessionUtil session = new SessionUtil();
        User uBusca = new User();
        
        //Pega sessao, ip e ultimologin
        uBusca.setSession((session.getSessao()).trim());
              
        UserJpaController dao = new UserJpaController(utx, emf);
        
        
        em = emf.createEntityManager();
        this.usuario = dao.findUsersForSessao(uBusca,em);
        
        
        //SimpleDateFormat formatdt = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        
        //Date date = new Date(usuario.getUltimologin().getTime()); 
        //setData(formatdt.format(date));
        
    }     
    
//    public String getData() {
//        return data;
//    }
//
//    /**
//     * @param data the data to set
//     */
//    public void setData(String data) {
//        this.data = data;
//    }
        
    
}
