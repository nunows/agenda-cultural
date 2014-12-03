/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import bd.Eventos;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import jpa.EventosJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import jsf.util.PagingInfo;

/**
 *
 * @author nuno
 */
public class EventosController {

    public EventosController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (EventosJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "eventosJpa");
        pagingInfo = new PagingInfo();
        converter = new EventosConverter();
    }
    private Eventos eventos = null;
    private List<Eventos> eventosItems = null;
    private EventosJpaController jpaController = null;
    private EventosConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getEventosCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getEventosItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findEventosEntities(), false);
    }

    public SelectItem[] getEventosItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findEventosEntities(), true);
    }

    public Eventos getEventos() {
        if (eventos == null) {
            eventos = (Eventos) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentEventos", converter, null);
        }
        if (eventos == null) {
            eventos = new Eventos();
        }
        return eventos;
    }

    public String listSetup() {
        reset(true);
        return "eventos_list";
    }

    public String createSetup() {
        reset(false);
        eventos = new Eventos();
        return "eventos_create";
    }

    public String create() {
        try {
            jpaController.create(eventos);
            JsfUtil.addSuccessMessage("Evento criado com sucesso!");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("eventos_detail");
    }

    public String editSetup() {
        return scalarSetup("eventos_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        eventos = (Eventos) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentEventos", converter, null);
        if (eventos == null) {
            String requestEventosString = JsfUtil.getRequestParameter("jsfcrud.currentEventos");
            JsfUtil.addErrorMessage("The eventos with id " + requestEventosString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String eventosString = converter.getAsString(FacesContext.getCurrentInstance(), null, eventos);
        String currentEventosString = JsfUtil.getRequestParameter("jsfcrud.currentEventos");
        if (eventosString == null || eventosString.length() == 0 || !eventosString.equals(currentEventosString)) {
            String outcome = editSetup();
            if ("eventos_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit eventos. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(eventos);
            JsfUtil.addSuccessMessage("Evento alterado com sucesso!");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentEventos");
        Integer id = new Integer(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Evento apagado com sucesso!");
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

    public List<Eventos> getEventosItems() {
        if (eventosItems == null) {
            getPagingInfo();
            eventosItems = jpaController.findEventosEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return eventosItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "eventos_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "eventos_list";
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
        eventos = null;
        eventosItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Eventos newEventos = new Eventos();
        String newEventosString = converter.getAsString(FacesContext.getCurrentInstance(), null, newEventos);
        String eventosString = converter.getAsString(FacesContext.getCurrentInstance(), null, eventos);
        if (!newEventosString.equals(eventosString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
