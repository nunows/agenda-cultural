/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import bd.TipoEventos;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.TipoEventosJpaController;

/**
 *
 * @author nuno
 */
public class TipoEventosConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Integer id = new Integer(string);
        TipoEventosJpaController controller = (TipoEventosJpaController) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "tipoEventosJpa");
        return controller.findTipoEventos(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof TipoEventos) {
            TipoEventos o = (TipoEventos) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: bd.TipoEventos");
        }
    }

}
