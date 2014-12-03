/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import bd.TipoEventos;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import jpa.TipoEventosJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.IllegalOrphanException;
import jsf.util.PagingInfo;

/**
 *
 * @author nuno
 */
public class TipoEventosController {

    public TipoEventosController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (TipoEventosJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "tipoEventosJpa");
        pagingInfo = new PagingInfo();
        converter = new TipoEventosConverter();
    }
    private TipoEventos tipoEventos = null;
    private List<TipoEventos> tipoEventosItems = null;
    private TipoEventosJpaController jpaController = null;
    private TipoEventosConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getTipoEventosCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getTipoEventosItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findTipoEventosEntities(), false);
    }

    public SelectItem[] getTipoEventosItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findTipoEventosEntities(), true);
    }

    public TipoEventos getTipoEventos() {
        if (tipoEventos == null) {
            tipoEventos = (TipoEventos) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentTipoEventos", converter, null);
        }
        if (tipoEventos == null) {
            tipoEventos = new TipoEventos();
        }
        return tipoEventos;
    }

    public String listSetup() {
        reset(true);
        return "tipoEventos_list";
    }

    public String createSetup() {
        reset(false);
        tipoEventos = new TipoEventos();
        return "tipoEventos_create";
    }

    public String create() {
        try {
            jpaController.create(tipoEventos);
            JsfUtil.addSuccessMessage("Tipo de Evento criado com sucesso!");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("tipoEventos_detail");
    }

    public String editSetup() {
        return scalarSetup("tipoEventos_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        tipoEventos = (TipoEventos) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentTipoEventos", converter, null);
        if (tipoEventos == null) {
            String requestTipoEventosString = JsfUtil.getRequestParameter("jsfcrud.currentTipoEventos");
            JsfUtil.addErrorMessage("The tipoEventos with id " + requestTipoEventosString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String tipoEventosString = converter.getAsString(FacesContext.getCurrentInstance(), null, tipoEventos);
        String currentTipoEventosString = JsfUtil.getRequestParameter("jsfcrud.currentTipoEventos");
        if (tipoEventosString == null || tipoEventosString.length() == 0 || !tipoEventosString.equals(currentTipoEventosString)) {
            String outcome = editSetup();
            if ("tipoEventos_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit tipoEventos. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(tipoEventos);
            JsfUtil.addSuccessMessage("Tipo de Evento alterado com sucesso!");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentTipoEventos");
        Integer id = new Integer(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Tipo de Evento apagado com sucesso!");
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

    public List<TipoEventos> getTipoEventosItems() {
        if (tipoEventosItems == null) {
            getPagingInfo();
            tipoEventosItems = jpaController.findTipoEventosEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return tipoEventosItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "tipoEventos_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "tipoEventos_list";
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
        tipoEventos = null;
        tipoEventosItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        TipoEventos newTipoEventos = new TipoEventos();
        String newTipoEventosString = converter.getAsString(FacesContext.getCurrentInstance(), null, newTipoEventos);
        String tipoEventosString = converter.getAsString(FacesContext.getCurrentInstance(), null, tipoEventos);
        if (!newTipoEventosString.equals(tipoEventosString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
