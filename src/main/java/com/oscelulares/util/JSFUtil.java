/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oscelulares.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author luciano
 */
public class JSFUtil {
     public static void adicionarMensagemSucesso(String mensagem){
        //parametros FacesMessage
        //1 - tipo da mensagem
        //2 - titulo
        //3 - mensagem
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,mensagem,mensagem);
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }
    
    public static void adicionarMensagemErro(String mensagem){
        //parametros FacesMessage
        //1 - tipo da mensagem
        //2 - titulo
        //3 - mensagem
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,mensagem);
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }
}
