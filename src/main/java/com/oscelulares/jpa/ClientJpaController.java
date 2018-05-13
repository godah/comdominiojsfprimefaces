/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oscelulares.jpa;

import com.oscelulares.jpa.exceptions.IllegalOrphanException;
import com.oscelulares.jpa.exceptions.NonexistentEntityException;
import com.oscelulares.jpa.exceptions.RollbackFailureException;
import com.oscelulares.model.Client;
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
public class ClientJpaController implements Serializable {

    public ClientJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Client client) throws RollbackFailureException, Exception {
        if (client.getServiceorderCollection() == null) {
            client.setServiceorderCollection(new ArrayList<Serviceorder>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Serviceorder> attachedServiceorderCollection = new ArrayList<Serviceorder>();
            for (Serviceorder serviceorderCollectionServiceorderToAttach : client.getServiceorderCollection()) {
                serviceorderCollectionServiceorderToAttach = em.getReference(serviceorderCollectionServiceorderToAttach.getClass(), serviceorderCollectionServiceorderToAttach.getId());
                attachedServiceorderCollection.add(serviceorderCollectionServiceorderToAttach);
            }
            client.setServiceorderCollection(attachedServiceorderCollection);
            em.persist(client);
            for (Serviceorder serviceorderCollectionServiceorder : client.getServiceorderCollection()) {
                Client oldClientIdOfServiceorderCollectionServiceorder = serviceorderCollectionServiceorder.getClientId();
                serviceorderCollectionServiceorder.setClientId(client);
                serviceorderCollectionServiceorder = em.merge(serviceorderCollectionServiceorder);
                if (oldClientIdOfServiceorderCollectionServiceorder != null) {
                    oldClientIdOfServiceorderCollectionServiceorder.getServiceorderCollection().remove(serviceorderCollectionServiceorder);
                    oldClientIdOfServiceorderCollectionServiceorder = em.merge(oldClientIdOfServiceorderCollectionServiceorder);
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

    public void edit(Client client) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Client persistentClient = em.find(Client.class, client.getId());
            Collection<Serviceorder> serviceorderCollectionOld = persistentClient.getServiceorderCollection();
            Collection<Serviceorder> serviceorderCollectionNew = client.getServiceorderCollection();
            List<String> illegalOrphanMessages = null;
            for (Serviceorder serviceorderCollectionOldServiceorder : serviceorderCollectionOld) {
                if (!serviceorderCollectionNew.contains(serviceorderCollectionOldServiceorder)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Serviceorder " + serviceorderCollectionOldServiceorder + " since its clientId field is not nullable.");
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
            client.setServiceorderCollection(serviceorderCollectionNew);
            client = em.merge(client);
            for (Serviceorder serviceorderCollectionNewServiceorder : serviceorderCollectionNew) {
                if (!serviceorderCollectionOld.contains(serviceorderCollectionNewServiceorder)) {
                    Client oldClientIdOfServiceorderCollectionNewServiceorder = serviceorderCollectionNewServiceorder.getClientId();
                    serviceorderCollectionNewServiceorder.setClientId(client);
                    serviceorderCollectionNewServiceorder = em.merge(serviceorderCollectionNewServiceorder);
                    if (oldClientIdOfServiceorderCollectionNewServiceorder != null && !oldClientIdOfServiceorderCollectionNewServiceorder.equals(client)) {
                        oldClientIdOfServiceorderCollectionNewServiceorder.getServiceorderCollection().remove(serviceorderCollectionNewServiceorder);
                        oldClientIdOfServiceorderCollectionNewServiceorder = em.merge(oldClientIdOfServiceorderCollectionNewServiceorder);
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
                Integer id = client.getId();
                if (findClient(id) == null) {
                    throw new NonexistentEntityException("The client with id " + id + " no longer exists.");
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
            Client client;
            try {
                client = em.getReference(Client.class, id);
                client.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The client with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Serviceorder> serviceorderCollectionOrphanCheck = client.getServiceorderCollection();
            for (Serviceorder serviceorderCollectionOrphanCheckServiceorder : serviceorderCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the Serviceorder " + serviceorderCollectionOrphanCheckServiceorder + " in its serviceorderCollection field has a non-nullable clientId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(client);
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

    public List<Client> findClientEntities() {
        return findClientEntities(true, -1, -1);
    }

    public List<Client> findClientEntities(int maxResults, int firstResult) {
        return findClientEntities(false, maxResults, firstResult);
    }

    private List<Client> findClientEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Client.class));
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

    public Client findClient(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Client.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Client> rt = cq.from(Client.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
