/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oscelulares.controller;

import com.oscelulares.model.Serviceorder;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luciano
 */
@Stateless
public class ServiceorderFacade extends AbstractFacade<Serviceorder> {

    @PersistenceContext(unitName = "com_oscelulares_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceorderFacade() {
        super(Serviceorder.class);
    }
    
}
