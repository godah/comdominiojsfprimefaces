/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oscelulares.util;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;

/**
 *
 * @author luciano
 */
public class Programs {
    
    public static Object getManagedBean(String MB){
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();
        //PedidosController pedidosController = (PedidosController) resolver.getValue(context.getELContext(), null, "pedidosController");
        return resolver.getValue(context.getELContext(), null, MB);
    }
            
       
}
