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
import com.oscelulares.model.Usertype;
import com.oscelulares.model.Serviceorder;
import com.oscelulares.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author luciano
 */
public class UserJpaController implements Serializable {

    public UserJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws RollbackFailureException, Exception {
        if (user.getServiceorderCollection() == null) {
            user.setServiceorderCollection(new ArrayList<Serviceorder>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usertype usertypeId = user.getUsertypeId();
            if (usertypeId != null) {
                usertypeId = em.getReference(usertypeId.getClass(), usertypeId.getId());
                user.setUsertypeId(usertypeId);
            }
            Collection<Serviceorder> attachedServiceorderCollection = new ArrayList<Serviceorder>();
            for (Serviceorder serviceorderCollectionServiceorderToAttach : user.getServiceorderCollection()) {
                serviceorderCollectionServiceorderToAttach = em.getReference(serviceorderCollectionServiceorderToAttach.getClass(), serviceorderCollectionServiceorderToAttach.getId());
                attachedServiceorderCollection.add(serviceorderCollectionServiceorderToAttach);
            }
            user.setServiceorderCollection(attachedServiceorderCollection);
            em.persist(user);
            if (usertypeId != null) {
                usertypeId.getUserCollection().add(user);
                usertypeId = em.merge(usertypeId);
            }
            for (Serviceorder serviceorderCollectionServiceorder : user.getServiceorderCollection()) {
                User oldUserIdOfServiceorderCollectionServiceorder = serviceorderCollectionServiceorder.getUserId();
                serviceorderCollectionServiceorder.setUserId(user);
                serviceorderCollectionServiceorder = em.merge(serviceorderCollectionServiceorder);
                if (oldUserIdOfServiceorderCollectionServiceorder != null) {
                    oldUserIdOfServiceorderCollectionServiceorder.getServiceorderCollection().remove(serviceorderCollectionServiceorder);
                    oldUserIdOfServiceorderCollectionServiceorder = em.merge(oldUserIdOfServiceorderCollectionServiceorder);
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

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User persistentUser = em.find(User.class, user.getId());
            Usertype usertypeIdOld = persistentUser.getUsertypeId();
            Usertype usertypeIdNew = user.getUsertypeId();
            Collection<Serviceorder> serviceorderCollectionOld = persistentUser.getServiceorderCollection();
            Collection<Serviceorder> serviceorderCollectionNew = user.getServiceorderCollection();
            List<String> illegalOrphanMessages = null;
            for (Serviceorder serviceorderCollectionOldServiceorder : serviceorderCollectionOld) {
                if (!serviceorderCollectionNew.contains(serviceorderCollectionOldServiceorder)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Serviceorder " + serviceorderCollectionOldServiceorder + " since its userId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usertypeIdNew != null) {
                usertypeIdNew = em.getReference(usertypeIdNew.getClass(), usertypeIdNew.getId());
                user.setUsertypeId(usertypeIdNew);
            }
            Collection<Serviceorder> attachedServiceorderCollectionNew = new ArrayList<Serviceorder>();
            for (Serviceorder serviceorderCollectionNewServiceorderToAttach : serviceorderCollectionNew) {
                serviceorderCollectionNewServiceorderToAttach = em.getReference(serviceorderCollectionNewServiceorderToAttach.getClass(), serviceorderCollectionNewServiceorderToAttach.getId());
                attachedServiceorderCollectionNew.add(serviceorderCollectionNewServiceorderToAttach);
            }
            serviceorderCollectionNew = attachedServiceorderCollectionNew;
            user.setServiceorderCollection(serviceorderCollectionNew);
            user = em.merge(user);
            if (usertypeIdOld != null && !usertypeIdOld.equals(usertypeIdNew)) {
                usertypeIdOld.getUserCollection().remove(user);
                usertypeIdOld = em.merge(usertypeIdOld);
            }
            if (usertypeIdNew != null && !usertypeIdNew.equals(usertypeIdOld)) {
                usertypeIdNew.getUserCollection().add(user);
                usertypeIdNew = em.merge(usertypeIdNew);
            }
            for (Serviceorder serviceorderCollectionNewServiceorder : serviceorderCollectionNew) {
                if (!serviceorderCollectionOld.contains(serviceorderCollectionNewServiceorder)) {
                    User oldUserIdOfServiceorderCollectionNewServiceorder = serviceorderCollectionNewServiceorder.getUserId();
                    serviceorderCollectionNewServiceorder.setUserId(user);
                    serviceorderCollectionNewServiceorder = em.merge(serviceorderCollectionNewServiceorder);
                    if (oldUserIdOfServiceorderCollectionNewServiceorder != null && !oldUserIdOfServiceorderCollectionNewServiceorder.equals(user)) {
                        oldUserIdOfServiceorderCollectionNewServiceorder.getServiceorderCollection().remove(serviceorderCollectionNewServiceorder);
                        oldUserIdOfServiceorderCollectionNewServiceorder = em.merge(oldUserIdOfServiceorderCollectionNewServiceorder);
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
                Integer id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Serviceorder> serviceorderCollectionOrphanCheck = user.getServiceorderCollection();
            for (Serviceorder serviceorderCollectionOrphanCheckServiceorder : serviceorderCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Serviceorder " + serviceorderCollectionOrphanCheckServiceorder + " in its serviceorderCollection field has a non-nullable userId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usertype usertypeId = user.getUsertypeId();
            if (usertypeId != null) {
                usertypeId.getUserCollection().remove(user);
                usertypeId = em.merge(usertypeId);
            }
            em.remove(user);
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

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public User findUsersForSessao(User u,EntityManager em) {
        String jpql = "SELECT u "
                + "FROM User u "
                + "WHERE u.session = :session ";
        
        User retorno;
        try {
            retorno = em.createQuery(jpql, User.class)
                .setParameter("session", u.getSession())
                .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
             retorno = new User();
        }
        
        return retorno;
    }

    public User login(User u, EntityManager em) {    
        String jpql = "SELECT u "
                + "FROM User u "
                + "WHERE u.login = :login AND u.passwd = :passwd";
        User retorno = new User();
        try {
            retorno = em.createQuery(jpql, User.class)
                .setParameter("login", u.getLogin())
                .setParameter("passwd", u.getPasswd())
                .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Usuário ou senha incorretos");
        }        
        return retorno;
    }

    public void registerLogin(User u) {
       
        try {
            edit(u);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(UserJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(UserJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    public void registerLogout(User u) {
        
        try {
            edit(u);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(UserJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(UserJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
