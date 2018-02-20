package com.oscelulares.controller;

import com.oscelulares.auth.SessionBean;
import com.oscelulares.model.Serviceorder;
import com.oscelulares.jsf.util.JsfUtil;
import com.oscelulares.jsf.util.JsfUtil.PersistAction;
import com.oscelulares.facecade.ServiceorderFacade;
import com.oscelulares.util.Programs;
import com.oscelulares.util.Senhas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
//import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

@Named("serviceorderController")
@SessionScoped
public class ServiceorderController implements Serializable {
    private EntityManager em = null;
    //private InitialContext ic = null;
    @PersistenceUnit(unitName="com_oscelulares_war_1.0-SNAPSHOTPU") //inject from your application server
    EntityManagerFactory emf;
    @Resource //inject from your application server
    UserTransaction utx; 

    @EJB
    private com.oscelulares.facecade.ServiceorderFacade ejbFacade;
    private List<Serviceorder> items = null;
    private Serviceorder selected;

    public ServiceorderController() {
    }

    public Serviceorder getSelected() {
        return selected;
    }

    public void setSelected(Serviceorder selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ServiceorderFacade getFacade() {
        return ejbFacade;
    }

    public Serviceorder prepareCreate() {
        selected = new Serviceorder();
        initializeEmbeddableKey();
        
        //atributos predefinidos
        selected.setCode(getLastCode());
        selected.setStatus('1'); //Analise Técnica
        selected.setTrackcode(trackcodegen());//gera trackcode único
        SessionBean sessionBean = (SessionBean) Programs.getManagedBean("MBSession");
        selected.setUserId(sessionBean.getUsuarios());//seleciona usuário atual
        selected.setDate(new Date());//pega data atual
        
        return selected;
    }

    public void create() {
        
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ServiceorderCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ServiceorderUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ServiceorderDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Serviceorder> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }
    
    public String trackcodegen(){
        em = emf.createEntityManager();
        String trackcode;
        while(true){
            trackcode = Senhas.gerarNovaSenha(8);
            if(!checkExistsTrackCode(trackcode, em))
                break;
        }
        return trackcode;
    }
    
    public int getLastCode() {
        em = emf.createEntityManager();
        String jpql = "SELECT max(s.code) "
                + "FROM Serviceorder s ";
        
        Integer newCode;
        try {
            newCode = em.createQuery(jpql, Integer.class)
                .getSingleResult();
            return newCode + 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public boolean checkExistsTrackCode(String trackcode,EntityManager em) {
        String jpql = "SELECT s "
                + "FROM Serviceorder s "
                + "WHERE s.trackcode = :trackcode ";
        
        Serviceorder OSTemp;
        try {
            OSTemp = em.createQuery(jpql, Serviceorder.class)
                .setParameter("trackcode", trackcode)
                .getSingleResult();
            System.out.println("achou trackcode " + trackcode);
            return true;
        } catch (Exception e) {
             System.out.println("nao achou trackcode " + trackcode);
             return false;
        }
    }

    public Serviceorder getServiceorder(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Serviceorder> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Serviceorder> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Serviceorder.class)
    public static class ServiceorderControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ServiceorderController controller = (ServiceorderController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "serviceorderController");
            return controller.getServiceorder(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Serviceorder) {
                Serviceorder o = (Serviceorder) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Serviceorder.class.getName()});
                return null;
            }
        }

    }

}
