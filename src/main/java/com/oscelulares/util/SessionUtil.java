/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oscelulares.util;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author luciano
 */
public class SessionUtil implements Serializable{
     public static void main(String[] args) {
        //testa getDataServer()
        //System.out.println("Data: " + getDataServer());
        
        //testa getTimeServer()
        //System.out.println("Data: " + getTimeServer());
        
        //testa getIP()
        //System.out.println("IP: "+ getIP());
        
        //testa getSessao()
       //System.out.println("Sessao: "+ getSessao());
    }
   
    private static final long serialVersionUID = 1L;

    public static HttpSession getSession() {
            FacesContext ctx = FacesContext.getCurrentInstance();
            HttpSession sessao = (HttpSession) ctx.getExternalContext().getSession(
                            false);
            return sessao;
    }

    public static void setParam(String key, Object value) {
            getSession().setAttribute(key, value);
    }

    public static Object getParam(String key) {
            return getSession().getAttribute(key);
    }

    public static void remove(String key) {
            getSession().removeAttribute(key);
    }

    public static void invalidate() {
            getSession().invalidate();
    }
    public static String getDataServer(){
    Date data = new Date(System.currentTimeMillis());  
    SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy"); 
    return formatarDate.format(data);
        
    }
    public static String getTimeServer(){
        Date data = new Date(System.currentTimeMillis());  
        SimpleDateFormat formatarTime = new SimpleDateFormat("HH:mm:ss");       
        return formatarTime.format(data);
    }
    
    public static Date getUltimoLogin(){
        Date data = new Date(System.currentTimeMillis());  
        return data;
   
//    Date data = null;
//    SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//    return formatarDate.format(data);
    
        
    }
    /** Identifica o endereço IP remoto*/
    public static String getIP() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        return request.getRemoteAddr();
    }
    
    /** Identifica o ID da sessão */
    public static String getSessao() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        return session.getId();
    }
}
