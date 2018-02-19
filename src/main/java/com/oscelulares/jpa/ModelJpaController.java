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
import com.oscelulares.model.Brand;
import com.oscelulares.model.Model;
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
public class ModelJpaController implements Serializable {

    public ModelJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Model model) throws RollbackFailureException, Exception {
        if (model.getServiceorderCollection() == null) {
            model.setServiceorderCollection(new ArrayList<Serviceorder>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Brand brandId = model.getBrandId();
            if (brandId != null) {
                brandId = em.getReference(brandId.getClass(), brandId.getId());
                model.setBrandId(brandId);
            }
            Collection<Serviceorder> attachedServiceorderCollection = new ArrayList<Serviceorder>();
            for (Serviceorder serviceorderCollectionServiceorderToAttach : model.getServiceorderCollection()) {
                serviceorderCollectionServiceorderToAttach = em.getReference(serviceorderCollectionServiceorderToAttach.getClass(), serviceorderCollectionServiceorderToAttach.getId());
                attachedServiceorderCollection.add(serviceorderCollectionServiceorderToAttach);
            }
            model.setServiceorderCollection(attachedServiceorderCollection);
            em.persist(model);
            if (brandId != null) {
                brandId.getModelCollection().add(model);
                brandId = em.merge(brandId);
            }
            for (Serviceorder serviceorderCollectionServiceorder : model.getServiceorderCollection()) {
                Model oldModelIdOfServiceorderCollectionServiceorder = serviceorderCollectionServiceorder.getModelId();
                serviceorderCollectionServiceorder.setModelId(model);
                serviceorderCollectionServiceorder = em.merge(serviceorderCollectionServiceorder);
                if (oldModelIdOfServiceorderCollectionServiceorder != null) {
                    oldModelIdOfServiceorderCollectionServiceorder.getServiceorderCollection().remove(serviceorderCollectionServiceorder);
                    oldModelIdOfServiceorderCollectionServiceorder = em.merge(oldModelIdOfServiceorderCollectionServiceorder);
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

    public void edit(Model model) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Model persistentModel = em.find(Model.class, model.getId());
            Brand brandIdOld = persistentModel.getBrandId();
            Brand brandIdNew = model.getBrandId();
            Collection<Serviceorder> serviceorderCollectionOld = persistentModel.getServiceorderCollection();
            Collection<Serviceorder> serviceorderCollectionNew = model.getServiceorderCollection();
            List<String> illegalOrphanMessages = null;
            for (Serviceorder serviceorderCollectionOldServiceorder : serviceorderCollectionOld) {
                if (!serviceorderCollectionNew.contains(serviceorderCollectionOldServiceorder)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Serviceorder " + serviceorderCollectionOldServiceorder + " since its modelId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (brandIdNew != null) {
                brandIdNew = em.getReference(brandIdNew.getClass(), brandIdNew.getId());
                model.setBrandId(brandIdNew);
            }
            Collection<Serviceorder> attachedServiceorderCollectionNew = new ArrayList<Serviceorder>();
            for (Serviceorder serviceorderCollectionNewServiceorderToAttach : serviceorderCollectionNew) {
                serviceorderCollectionNewServiceorderToAttach = em.getReference(serviceorderCollectionNewServiceorderToAttach.getClass(), serviceorderCollectionNewServiceorderToAttach.getId());
                attachedServiceorderCollectionNew.add(serviceorderCollectionNewServiceorderToAttach);
            }
            serviceorderCollectionNew = attachedServiceorderCollectionNew;
            model.setServiceorderCollection(serviceorderCollectionNew);
            model = em.merge(model);
            if (brandIdOld != null && !brandIdOld.equals(brandIdNew)) {
                brandIdOld.getModelCollection().remove(model);
                brandIdOld = em.merge(brandIdOld);
            }
            if (brandIdNew != null && !brandIdNew.equals(brandIdOld)) {
                brandIdNew.getModelCollection().add(model);
                brandIdNew = em.merge(brandIdNew);
            }
            for (Serviceorder serviceorderCollectionNewServiceorder : serviceorderCollectionNew) {
                if (!serviceorderCollectionOld.contains(serviceorderCollectionNewServiceorder)) {
                    Model oldModelIdOfServiceorderCollectionNewServiceorder = serviceorderCollectionNewServiceorder.getModelId();
                    serviceorderCollectionNewServiceorder.setModelId(model);
                    serviceorderCollectionNewServiceorder = em.merge(serviceorderCollectionNewServiceorder);
                    if (oldModelIdOfServiceorderCollectionNewServiceorder != null && !oldModelIdOfServiceorderCollectionNewServiceorder.equals(model)) {
                        oldModelIdOfServiceorderCollectionNewServiceorder.getServiceorderCollection().remove(serviceorderCollectionNewServiceorder);
                        oldModelIdOfServiceorderCollectionNewServiceorder = em.merge(oldModelIdOfServiceorderCollectionNewServiceorder);
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
                Integer id = model.getId();
                if (findModel(id) == null) {
                    throw new NonexistentEntityException("The model with id " + id + " no longer exists.");
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
            Model model;
            try {
                model = em.getReference(Model.class, id);
                model.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The model with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Serviceorder> serviceorderCollectionOrphanCheck = model.getServiceorderCollection();
            for (Serviceorder serviceorderCollectionOrphanCheckServiceorder : serviceorderCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Model (" + model + ") cannot be destroyed since the Serviceorder " + serviceorderCollectionOrphanCheckServiceorder + " in its serviceorderCollection field has a non-nullable modelId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Brand brandId = model.getBrandId();
            if (brandId != null) {
                brandId.getModelCollection().remove(model);
                brandId = em.merge(brandId);
            }
            em.remove(model);
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

    public List<Model> findModelEntities() {
        return findModelEntities(true, -1, -1);
    }

    public List<Model> findModelEntities(int maxResults, int firstResult) {
        return findModelEntities(false, maxResults, firstResult);
    }

    private List<Model> findModelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Model.class));
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

    public Model findModel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Model.class, id);
        } finally {
            em.close();
        }
    }

    public int getModelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Model> rt = cq.from(Model.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
