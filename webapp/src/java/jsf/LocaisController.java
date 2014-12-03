/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import bd.Locais;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import jpa.LocaisJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.IllegalOrphanException;
import jsf.util.PagingInfo;

/**
 *
 * @author nuno
 */
public class LocaisController {

    public LocaisController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (LocaisJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "locaisJpa");
        pagingInfo = new PagingInfo();
        converter = new LocaisConverter();
    }
    private Locais locais = null;
    private List<Locais> locaisItems = null;
    private LocaisJpaController jpaController = null;
    private LocaisConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getLocaisCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getLocaisItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findLocaisEntities(), false);
    }

    public SelectItem[] getLocaisItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findLocaisEntities(), true);
    }

    public Locais getLocais() {
        if (locais == null) {
            locais = (Locais) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentLocais", converter, null);
        }
        if (locais == null) {
            locais = new Locais();
        }
        return locais;
    }

    public String listSetup() {
        reset(true);
        return "locais_list";
    }

    public String createSetup() {
        reset(false);
        locais = new Locais();
        return "locais_create";
    }

    public String create() {
        try {
            jpaController.create(locais);
            JsfUtil.addSuccessMessage("Local criado com sucesso!");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("locais_detail");
    }

    public String editSetup() {
        return scalarSetup("locais_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        locais = (Locais) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentLocais", converter, null);
        if (locais == null) {
            String requestLocaisString = JsfUtil.getRequestParameter("jsfcrud.currentLocais");
            JsfUtil.addErrorMessage("The locais with id " + requestLocaisString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String locaisString = converter.getAsString(FacesContext.getCurrentInstance(), null, locais);
        String currentLocaisString = JsfUtil.getRequestParameter("jsfcrud.currentLocais");
        if (locaisString == null || locaisString.length() == 0 || !locaisString.equals(currentLocaisString)) {
            String outcome = editSetup();
            if ("locais_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit locais. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(locais);
            JsfUtil.addSuccessMessage("Local alterado com sucesso!");
        } catch (IllegalOrphanException oe) {
            JsfUtil.addErrorMessages(oe.getMessages());
            return null;
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return detailSetup();
    }

    public String destroy() {
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentLocais");
        Integer id = new Integer(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Local apagado com sucesso!");
        } catch (IllegalOrphanException oe) {
            JsfUtil.addErrorMessages(oe.getMessages());
            return null;
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return relatedOrListOutcome();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return relatedOrListOutcome();
    }

    private String relatedOrListOutcome() {
        String relatedControllerOutcome = relatedControllerOutcome();
        if (relatedControllerOutcome != null) {
            return relatedControllerOutcome;
        }
        return listSetup();
    }

    public List<Locais> getLocaisItems() {
        if (locaisItems == null) {
            getPagingInfo();
            locaisItems = jpaController.findLocaisEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return locaisItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "locais_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "locais_list";
    }

    private String relatedControllerOutcome() {
        String relatedControllerString = JsfUtil.getRequestParameter("jsfcrud.relatedController");
        String relatedControllerTypeString = JsfUtil.getRequestParameter("jsfcrud.relatedControllerType");
        if (relatedControllerString != null && relatedControllerTypeString != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            Object relatedController = context.getApplication().getELResolver().getValue(context.getELContext(), null, relatedControllerString);
            try {
                Class<?> relatedControllerType = Class.forName(relatedControllerTypeString);
                Method detailSetupMethod = relatedControllerType.getMethod("detailSetup");
                return (String) detailSetupMethod.invoke(relatedController);
            } catch (ClassNotFoundException e) {
                throw new FacesException(e);
            } catch (NoSuchMethodException e) {
                throw new FacesException(e);
            } catch (IllegalAccessException e) {
                throw new FacesException(e);
            } catch (InvocationTargetException e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    private void reset(boolean resetFirstItem) {
        locais = null;
        locaisItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Locais newLocais = new Locais();
        String newLocaisString = converter.getAsString(FacesContext.getCurrentInstance(), null, newLocais);
        String locaisString = converter.getAsString(FacesContext.getCurrentInstance(), null, locais);
        if (!newLocaisString.equals(locaisString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
