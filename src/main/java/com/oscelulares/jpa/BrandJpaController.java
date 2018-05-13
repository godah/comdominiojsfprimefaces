/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oscelulares.jpa;

import com.oscelulares.jpa.exceptions.IllegalOrphanException;
import com.oscelulares.jpa.exceptions.NonexistentEntityException;
import com.oscelulares.jpa.exceptions.RollbackFailureException;
import com.oscelulares.model.Brand;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.oscelulares.model.Model;
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
public class BrandJpaController implements Serializable {

    public BrandJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Brand brand) throws RollbackFailureException, Exception {
        if (brand.getModelCollection() == null) {
            brand.setModelCollection(new ArrayList<Model>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Model> attachedModelCollection = new ArrayList<Model>();
            for (Model modelCollectionModelToAttach : brand.getModelCollection()) {
                modelCollectionModelToAttach = em.getReference(modelCollectionModelToAttach.getClass(), modelCollectionModelToAttach.getId());
                attachedModelCollection.add(modelCollectionModelToAttach);
            }
            brand.setModelCollection(attachedModelCollection);
            em.persist(brand);
            for (Model modelCollectionModel : brand.getModelCollection()) {
                Brand oldBrandIdOfModelCollectionModel = modelCollectionModel.getBrandId();
                modelCollectionModel.setBrandId(brand);
                modelCollectionModel = em.merge(modelCollectionModel);
                if (oldBrandIdOfModelCollectionModel != null) {
                    oldBrandIdOfModelCollectionModel.getModelCollection().remove(modelCollectionModel);
                    oldBrandIdOfModelCollectionModel = em.merge(oldBrandIdOfModelCollectionModel);
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

    public void edit(Brand brand) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Brand persistentBrand = em.find(Brand.class, brand.getId());
            Collection<Model> modelCollectionOld = persistentBrand.getModelCollection();
            Collection<Model> modelCollectionNew = brand.getModelCollection();
            List<String> illegalOrphanMessages = null;
            for (Model modelCollectionOldModel : modelCollectionOld) {
                if (!modelCollectionNew.contains(modelCollectionOldModel)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Model " + modelCollectionOldModel + " since its brandId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Model> attachedModelCollectionNew = new ArrayList<Model>();
            for (Model modelCollectionNewModelToAttach : modelCollectionNew) {
                modelCollectionNewModelToAttach = em.getReference(modelCollectionNewModelToAttach.getClass(), modelCollectionNewModelToAttach.getId());
                attachedModelCollectionNew.add(modelCollectionNewModelToAttach);
            }
            modelCollectionNew = attachedModelCollectionNew;
            brand.setModelCollection(modelCollectionNew);
            brand = em.merge(brand);
            for (Model modelCollectionNewModel : modelCollectionNew) {
                if (!modelCollectionOld.contains(modelCollectionNewModel)) {
                    Brand oldBrandIdOfModelCollectionNewModel = modelCollectionNewModel.getBrandId();
                    modelCollectionNewModel.setBrandId(brand);
                    modelCollectionNewModel = em.merge(modelCollectionNewModel);
                    if (oldBrandIdOfModelCollectionNewModel != null && !oldBrandIdOfModelCollectionNewModel.equals(brand)) {
                        oldBrandIdOfModelCollectionNewModel.getModelCollection().remove(modelCollectionNewModel);
                        oldBrandIdOfModelCollectionNewModel = em.merge(oldBrandIdOfModelCollectionNewModel);
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
                Integer id = brand.getId();
                if (findBrand(id) == null) {
                    throw new NonexistentEntityException("The brand with id " + id + " no longer exists.");
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
            Brand brand;
            try {
                brand = em.getReference(Brand.class, id);
                brand.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The brand with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Model> modelCollectionOrphanCheck = brand.getModelCollection();
            for (Model modelCollectionOrphanCheckModel : modelCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Brand (" + brand + ") cannot be destroyed since the Model " + modelCollectionOrphanCheckModel + " in its modelCollection field has a non-nullable brandId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(brand);
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

    public List<Brand> findBrandEntities() {
        return findBrandEntities(true, -1, -1);
    }

    public List<Brand> findBrandEntities(int maxResults, int firstResult) {
        return findBrandEntities(false, maxResults, firstResult);
    }

    private List<Brand> findBrandEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Brand.class));
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

    public Brand findBrand(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Brand.class, id);
        } finally {
            em.close();
        }
    }

    public int getBrandCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Brand> rt = cq.from(Brand.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
