/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comdomino.controller;

import com.comdomino.model.Tipounidade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lobo
 */
@Stateless
public class TipounidadeFacade extends AbstractFacade<Tipounidade> {

    @PersistenceContext(unitName = "comdominoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipounidadeFacade() {
        super(Tipounidade.class);
    }
    
}
