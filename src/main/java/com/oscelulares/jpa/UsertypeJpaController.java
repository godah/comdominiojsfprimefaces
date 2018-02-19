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
import com.oscelulares.model.User;
import com.oscelulares.model.Usertype;
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
public class UsertypeJpaController implements Serializable {

    public UsertypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usertype usertype) throws RollbackFailureException, Exception {
        if (usertype.getUserCollection() == null) {
            usertype.setUserCollection(new ArrayList<User>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<User> attachedUserCollection = new ArrayList<User>();
            for (User userCollectionUserToAttach : usertype.getUserCollection()) {
                userCollectionUserToAttach = em.getReference(userCollectionUserToAttach.getClass(), userCollectionUserToAttach.getId());
                attachedUserCollection.add(userCollectionUserToAttach);
            }
            usertype.setUserCollection(attachedUserCollection);
            em.persist(usertype);
            for (User userCollectionUser : usertype.getUserCollection()) {
                Usertype oldUsertypeIdOfUserCollectionUser = userCollectionUser.getUsertypeId();
                userCollectionUser.setUsertypeId(usertype);
                userCollectionUser = em.merge(userCollectionUser);
                if (oldUsertypeIdOfUserCollectionUser != null) {
                    oldUsertypeIdOfUserCollectionUser.getUserCollection().remove(userCollectionUser);
                    oldUsertypeIdOfUserCollectionUser = em.merge(oldUsertypeIdOfUserCollectionUser);
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

    public void edit(Usertype usertype) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usertype persistentUsertype = em.find(Usertype.class, usertype.getId());
            Collection<User> userCollectionOld = persistentUsertype.getUserCollection();
            Collection<User> userCollectionNew = usertype.getUserCollection();
            List<String> illegalOrphanMessages = null;
            for (User userCollectionOldUser : userCollectionOld) {
                if (!userCollectionNew.contains(userCollectionOldUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain User " + userCollectionOldUser + " since its usertypeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<User> attachedUserCollectionNew = new ArrayList<User>();
            for (User userCollectionNewUserToAttach : userCollectionNew) {
                userCollectionNewUserToAttach = em.getReference(userCollectionNewUserToAttach.getClass(), userCollectionNewUserToAttach.getId());
                attachedUserCollectionNew.add(userCollectionNewUserToAttach);
            }
            userCollectionNew = attachedUserCollectionNew;
            usertype.setUserCollection(userCollectionNew);
            usertype = em.merge(usertype);
            for (User userCollectionNewUser : userCollectionNew) {
                if (!userCollectionOld.contains(userCollectionNewUser)) {
                    Usertype oldUsertypeIdOfUserCollectionNewUser = userCollectionNewUser.getUsertypeId();
                    userCollectionNewUser.setUsertypeId(usertype);
                    userCollectionNewUser = em.merge(userCollectionNewUser);
                    if (oldUsertypeIdOfUserCollectionNewUser != null && !oldUsertypeIdOfUserCollectionNewUser.equals(usertype)) {
                        oldUsertypeIdOfUserCollectionNewUser.getUserCollection().remove(userCollectionNewUser);
                        oldUsertypeIdOfUserCollectionNewUser = em.merge(oldUsertypeIdOfUserCollectionNewUser);
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
                Integer id = usertype.getId();
                if (findUsertype(id) == null) {
                    throw new NonexistentEntityException("The usertype with id " + id + " no longer exists.");
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
            Usertype usertype;
            try {
                usertype = em.getReference(Usertype.class, id);
                usertype.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usertype with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<User> userCollectionOrphanCheck = usertype.getUserCollection();
            for (User userCollectionOrphanCheckUser : userCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usertype (" + usertype + ") cannot be destroyed since the User " + userCollectionOrphanCheckUser + " in its userCollection field has a non-nullable usertypeId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usertype);
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

    public List<Usertype> findUsertypeEntities() {
        return findUsertypeEntities(true, -1, -1);
    }

    public List<Usertype> findUsertypeEntities(int maxResults, int firstResult) {
        return findUsertypeEntities(false, maxResults, firstResult);
    }

    private List<Usertype> findUsertypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usertype.class));
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

    public Usertype findUsertype(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usertype.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsertypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usertype> rt = cq.from(Usertype.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
