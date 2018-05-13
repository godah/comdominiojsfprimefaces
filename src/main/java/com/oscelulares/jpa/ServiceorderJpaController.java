/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oscelulares.jpa;

import com.oscelulares.jpa.exceptions.IllegalOrphanException;
import com.oscelulares.jpa.exceptions.NonexistentEntityException;
import com.oscelulares.jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.oscelulares.model.Client;
import com.oscelulares.model.Model;
import com.oscelulares.model.Carrierservice;
import com.oscelulares.model.User;
import com.oscelulares.model.Budget;
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
public class ServiceorderJpaController implements Serializable {

    public ServiceorderJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Serviceorder serviceorder) throws RollbackFailureException, Exception {
        if (serviceorder.getBudgetCollection() == null) {
            serviceorder.setBudgetCollection(new ArrayList<Budget>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Client clientId = serviceorder.getClientId();
            if (clientId != null) {
                clientId = em.getReference(clientId.getClass(), clientId.getId());
                serviceorder.setClientId(clientId);
            }
            Model modelId = serviceorder.getModelId();
            if (modelId != null) {
                modelId = em.getReference(modelId.getClass(), modelId.getId());
                serviceorder.setModelId(modelId);
            }
            Carrierservice carrierserviceId = serviceorder.getCarrierserviceId();
            if (carrierserviceId != null) {
                carrierserviceId = em.getReference(carrierserviceId.getClass(), carrierserviceId.getId());
                serviceorder.setCarrierserviceId(carrierserviceId);
            }
            User userId = serviceorder.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                serviceorder.setUserId(userId);
            }
            Collection<Budget> attachedBudgetCollection = new ArrayList<Budget>();
            for (Budget budgetCollectionBudgetToAttach : serviceorder.getBudgetCollection()) {
                budgetCollectionBudgetToAttach = em.getReference(budgetCollectionBudgetToAttach.getClass(), budgetCollectionBudgetToAttach.getId());
                attachedBudgetCollection.add(budgetCollectionBudgetToAttach);
            }
            serviceorder.setBudgetCollection(attachedBudgetCollection);
            em.persist(serviceorder);
            if (clientId != null) {
                clientId.getServiceorderCollection().add(serviceorder);
                clientId = em.merge(clientId);
            }
            if (modelId != null) {
                modelId.getServiceorderCollection().add(serviceorder);
                modelId = em.merge(modelId);
            }
            if (carrierserviceId != null) {
                carrierserviceId.getServiceorderCollection().add(serviceorder);
                carrierserviceId = em.merge(carrierserviceId);
            }
            if (userId != null) {
                userId.getServiceorderCollection().add(serviceorder);
                userId = em.merge(userId);
            }
            for (Budget budgetCollectionBudget : serviceorder.getBudgetCollection()) {
                Serviceorder oldServiceorderIdOfBudgetCollectionBudget = budgetCollectionBudget.getServiceorderId();
                budgetCollectionBudget.setServiceorderId(serviceorder);
                budgetCollectionBudget = em.merge(budgetCollectionBudget);
                if (oldServiceorderIdOfBudgetCollectionBudget != null) {
                    oldServiceorderIdOfBudgetCollectionBudget.getBudgetCollection().remove(budgetCollectionBudget);
                    oldServiceorderIdOfBudgetCollectionBudget = em.merge(oldServiceorderIdOfBudgetCollectionBudget);
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

    public void edit(Serviceorder serviceorder) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Serviceorder persistentServiceorder = em.find(Serviceorder.class, serviceorder.getId());
            Client clientIdOld = persistentServiceorder.getClientId();
            Client clientIdNew = serviceorder.getClientId();
            Model modelIdOld = persistentServiceorder.getModelId();
            Model modelIdNew = serviceorder.getModelId();
            Carrierservice carrierserviceIdOld = persistentServiceorder.getCarrierserviceId();
            Carrierservice carrierserviceIdNew = serviceorder.getCarrierserviceId();
            User userIdOld = persistentServiceorder.getUserId();
            User userIdNew = serviceorder.getUserId();
            Collection<Budget> budgetCollectionOld = persistentServiceorder.getBudgetCollection();
            Collection<Budget> budgetCollectionNew = serviceorder.getBudgetCollection();
            List<String> illegalOrphanMessages = null;
            for (Budget budgetCollectionOldBudget : budgetCollectionOld) {
                if (!budgetCollectionNew.contains(budgetCollectionOldBudget)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Budget " + budgetCollectionOldBudget + " since its serviceorderId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientIdNew != null) {
                clientIdNew = em.getReference(clientIdNew.getClass(), clientIdNew.getId());
                serviceorder.setClientId(clientIdNew);
            }
            if (modelIdNew != null) {
                modelIdNew = em.getReference(modelIdNew.getClass(), modelIdNew.getId());
                serviceorder.setModelId(modelIdNew);
            }
            if (carrierserviceIdNew != null) {
                carrierserviceIdNew = em.getReference(carrierserviceIdNew.getClass(), carrierserviceIdNew.getId());
                serviceorder.setCarrierserviceId(carrierserviceIdNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                serviceorder.setUserId(userIdNew);
            }
            Collection<Budget> attachedBudgetCollectionNew = new ArrayList<Budget>();
            for (Budget budgetCollectionNewBudgetToAttach : budgetCollectionNew) {
                budgetCollectionNewBudgetToAttach = em.getReference(budgetCollectionNewBudgetToAttach.getClass(), budgetCollectionNewBudgetToAttach.getId());
                attachedBudgetCollectionNew.add(budgetCollectionNewBudgetToAttach);
            }
            budgetCollectionNew = attachedBudgetCollectionNew;
            serviceorder.setBudgetCollection(budgetCollectionNew);
            serviceorder = em.merge(serviceorder);
            if (clientIdOld != null && !clientIdOld.equals(clientIdNew)) {
                clientIdOld.getServiceorderCollection().remove(serviceorder);
                clientIdOld = em.merge(clientIdOld);
            }
            if (clientIdNew != null && !clientIdNew.equals(clientIdOld)) {
                clientIdNew.getServiceorderCollection().add(serviceorder);
                clientIdNew = em.merge(clientIdNew);
            }
            if (modelIdOld != null && !modelIdOld.equals(modelIdNew)) {
                modelIdOld.getServiceorderCollection().remove(serviceorder);
                modelIdOld = em.merge(modelIdOld);
            }
            if (modelIdNew != null && !modelIdNew.equals(modelIdOld)) {
                modelIdNew.getServiceorderCollection().add(serviceorder);
                modelIdNew = em.merge(modelIdNew);
            }
            if (carrierserviceIdOld != null && !carrierserviceIdOld.equals(carrierserviceIdNew)) {
                carrierserviceIdOld.getServiceorderCollection().remove(serviceorder);
                carrierserviceIdOld = em.merge(carrierserviceIdOld);
            }
            if (carrierserviceIdNew != null && !carrierserviceIdNew.equals(carrierserviceIdOld)) {
                carrierserviceIdNew.getServiceorderCollection().add(serviceorder);
                carrierserviceIdNew = em.merge(carrierserviceIdNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getServiceorderCollection().remove(serviceorder);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getServiceorderCollection().add(serviceorder);
                userIdNew = em.merge(userIdNew);
            }
            for (Budget budgetCollectionNewBudget : budgetCollectionNew) {
                if (!budgetCollectionOld.contains(budgetCollectionNewBudget)) {
                    Serviceorder oldServiceorderIdOfBudgetCollectionNewBudget = budgetCollectionNewBudget.getServiceorderId();
                    budgetCollectionNewBudget.setServiceorderId(serviceorder);
                    budgetCollectionNewBudget = em.merge(budgetCollectionNewBudget);
                    if (oldServiceorderIdOfBudgetCollectionNewBudget != null && !oldServiceorderIdOfBudgetCollectionNewBudget.equals(serviceorder)) {
                        oldServiceorderIdOfBudgetCollectionNewBudget.getBudgetCollection().remove(budgetCollectionNewBudget);
                        oldServiceorderIdOfBudgetCollectionNewBudget = em.merge(oldServiceorderIdOfBudgetCollectionNewBudget);
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
                Integer id = serviceorder.getId();
                if (findServiceorder(id) == null) {
                    throw new NonexistentEntityException("The serviceorder with id " + id + " no longer exists.");
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
            Serviceorder serviceorder;
            try {
                serviceorder = em.getReference(Serviceorder.class, id);
                serviceorder.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The serviceorder with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Budget> budgetCollectionOrphanCheck = serviceorder.getBudgetCollection();
            for (Budget budgetCollectionOrphanCheckBudget : budgetCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Serviceorder (" + serviceorder + ") cannot be destroyed since the Budget " + budgetCollectionOrphanCheckBudget + " in its budgetCollection field has a non-nullable serviceorderId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Client clientId = serviceorder.getClientId();
            if (clientId != null) {
                clientId.getServiceorderCollection().remove(serviceorder);
                clientId = em.merge(clientId);
            }
            Model modelId = serviceorder.getModelId();
            if (modelId != null) {
                modelId.getServiceorderCollection().remove(serviceorder);
                modelId = em.merge(modelId);
            }
            Carrierservice carrierserviceId = serviceorder.getCarrierserviceId();
            if (carrierserviceId != null) {
                carrierserviceId.getServiceorderCollection().remove(serviceorder);
                carrierserviceId = em.merge(carrierserviceId);
            }
            User userId = serviceorder.getUserId();
            if (userId != null) {
                userId.getServiceorderCollection().remove(serviceorder);
                userId = em.merge(userId);
            }
            em.remove(serviceorder);
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

    public List<Serviceorder> findServiceorderEntities() {
        return findServiceorderEntities(true, -1, -1);
    }

    public List<Serviceorder> findServiceorderEntities(int maxResults, int firstResult) {
        return findServiceorderEntities(false, maxResults, firstResult);
    }

    private List<Serviceorder> findServiceorderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Serviceorder.class));
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

    public Serviceorder findServiceorder(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Serviceorder.class, id);
        } finally {
            em.close();
        }
    }

    public int getServiceorderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Serviceorder> rt = cq.from(Serviceorder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
