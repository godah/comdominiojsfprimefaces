/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oscelulares.facecade;

import com.oscelulares.model.Carrierservice;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luciano
 */
@Stateless
public class CarrierserviceFacade extends AbstractFacade<Carrierservice> {

    @PersistenceContext(unitName = "com_oscelulares_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarrierserviceFacade() {
        super(Carrierservice.class);
    }
    
}
