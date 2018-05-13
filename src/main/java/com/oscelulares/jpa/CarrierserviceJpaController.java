/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oscelulares.jpa;

import com.oscelulares.jpa.exceptions.IllegalOrphanException;
import com.oscelulares.jpa.exceptions.NonexistentEntityException;
import com.oscelulares.jpa.exceptions.RollbackFailureException;
import com.oscelulares.model.Carrierservice;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.oscelulares.model.Serviceorder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author luciano
 */
public class CarrierserviceJpaController implements Serializable {

    public CarrierserviceJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carrierservice carrierservice) throws RollbackFailureException, Exception {
        if (carrierservice.getServiceorderCollection() == null) {
            carrierservice.setServiceorderCollection(new ArrayList<Serviceorder>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Serviceorder> attachedServiceorderCollection = new ArrayList<Serviceorder>();
            for (Serviceorder serviceorderCollectionServiceorderToAttach : carrierservice.getServiceorderCollection()) {
                serviceorderCollectionServiceorderToAttach = em.getReference(serviceorderCollectionServiceorderToAttach.getClass(), serviceorderCollectionServiceorderToAttach.getId());
                attachedServiceorderCollection.add(serviceorderCollectionServiceorderToAttach);
            }
            carrierservice.setServiceorderCollection(attachedServiceorderCollection);
            em.persist(carrierservice);
            for (Serviceorder serviceorderCollectionServiceorder : carrierservice.getServiceorderCollection()) {
                Carrierservice oldCarrierserviceIdOfServiceorderCollectionServiceorder = serviceorderCollectionServiceorder.getCarrierserviceId();
                serviceorderCollectionServiceorder.setCarrierserviceId(carrierservice);
                serviceorderCollectionServiceorder = em.merge(serviceorderCollectionServiceorder);
                if (oldCarrierserviceIdOfServiceorderCollectionServiceorder != null) {
                    oldCarrierserviceIdOfServiceorderCollectionServiceorder.getServiceorderCollection().remove(serviceorderCollectionServiceorder);
                    oldCarrierserviceIdOfServiceorderCollectionServiceorder = em.merge(oldCarrierserviceIdOfServiceorderCollectionServiceorder);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carrierservice carrierservice) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Carrierservice persistentCarrierservice = em.find(Carrierservice.class, carrierservice.getId());
            Collection<Serviceorder> serviceorderCollectionOld = persistentCarrierservice.getServiceorderCollection();
            Collection<Serviceorder> serviceorderCollectionNew = carrierservice.getServiceorderCollection();
            List<String> illegalOrphanMessages = null;
            for (Serviceorder serviceorderCollectionOldServiceorder : serviceorderCollectionOld) {
                if (!serviceorderCollectionNew.contains(serviceorderCollectionOldServiceorder)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Serviceorder " + serviceorderCollectionOldServiceorder + " since its carrierserviceId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Serviceorder> attachedServiceorderCollectionNew = new ArrayList<Serviceorder>();
            for (Serviceorder serviceorderCollectionNewServiceorderToAttach : serviceorderCollectionNew) {
                serviceorderCollectionNewServiceorderToAttach = em.getReference(serviceorderCollectionNewServiceorderToAttach.getClass(), serviceorderCollectionNewServiceorderToAttach.getId());
                attachedServiceorderCollectionNew.add(serviceorderCollectionNewServiceorderToAttach);
            }
            serviceorderCollectionNew = attachedServiceorderCollectionNew;
            carrierservice.setServiceorderCollection(serviceorderCollectionNew);
            carrierservice = em.merge(carrierservice);
            for (Serviceorder serviceorderCollectionNewServiceorder : serviceorderCollectionNew) {
                if (!serviceorderCollectionOld.contains(serviceorderCollectionNewServiceorder)) {
                    Carrierservice oldCarrierserviceIdOfServiceorderCollectionNewServiceorder = serviceorderCollectionNewServiceorder.getCarrierserviceId();
                    serviceorderCollectionNewServiceorder.setCarrierserviceId(carrierservice);
                    serviceorderCollectionNewServiceorder = em.merge(serviceorderCollectionNewServiceorder);
                    if (oldCarrierserviceIdOfServiceorderCollectionNewServiceorder != null && !oldCarrierserviceIdOfServiceorderCollectionNewServiceorder.equals(carrierservice)) {
                        oldCarrierserviceIdOfServiceorderCollectionNewServiceorder.getServiceorderCollection().remove(serviceorderCollectionNewServiceorder);
                        oldCarrierserviceIdOfServiceorderCollectionNewServiceorder = em.merge(oldCarrierserviceIdOfServiceorderCollectionNewServiceorder);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = carrierservice.getId();
                if (findCarrierservice(id) == null) {
                    throw new NonexistentEntityException("The carrierservice with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Carrierservice carrierservice;
            try {
                carrierservice = em.getReference(Carrierservice.class, id);
                carrierservice.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carrierservice with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Serviceorder> serviceorderCollectionOrphanCheck = carrierservice.getServiceorderCollection();
            for (Serviceorder serviceorderCollectionOrphanCheckServiceorder : serviceorderCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Carrierservice (" + carrierservice + ") cannot be destroyed since the Serviceorder " + serviceorderCollectionOrphanCheckServiceorder + " in its serviceorderCollection field has a non-nullable carrierserviceId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(carrierservice);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carrierservice> findCarrierserviceEntities() {
        return findCarrierserviceEntities(true, -1, -1);
    }

    public List<Carrierservice> findCarrierserviceEntities(int maxResults, int firstResult) {
        return findCarrierserviceEntities(false, maxResults, firstResult);
    }

    private List<Carrierservice> findCarrierserviceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carrierservice.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Carrierservice findCarrierservice(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carrierservice.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarrierserviceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carrierservice> rt = cq.from(Carrierservice.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
