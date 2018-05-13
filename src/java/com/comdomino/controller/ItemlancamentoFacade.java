/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comdomino.controller;

import com.comdomino.model.Itemlancamento;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lobo
 */
@Stateless
public class ItemlancamentoFacade extends AbstractFacade<Itemlancamento> {

    @PersistenceContext(unitName = "comdominoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemlancamentoFacade() {
        super(Itemlancamento.class);
    }
    
}
