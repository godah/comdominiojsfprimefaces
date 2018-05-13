/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comdomino.controller;

import com.comdomino.model.Condominio;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lobo
 */
@Stateless
public class CondominioFacade extends AbstractFacade<Condominio> {

    @PersistenceContext(unitName = "comdominoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CondominioFacade() {
        super(Condominio.class);
    }
    
}
