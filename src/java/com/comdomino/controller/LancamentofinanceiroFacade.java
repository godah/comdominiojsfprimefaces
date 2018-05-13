/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comdomino.controller;

import com.comdomino.model.Lancamentofinanceiro;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lobo
 */
@Stateless
public class LancamentofinanceiroFacade extends AbstractFacade<Lancamentofinanceiro> {

    @PersistenceContext(unitName = "comdominoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LancamentofinanceiroFacade() {
        super(Lancamentofinanceiro.class);
    }
    
}
