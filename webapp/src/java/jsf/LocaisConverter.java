/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import bd.Locais;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.LocaisJpaController;

/**
 *
 * @author nuno
 */
public class LocaisConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Integer id = new Integer(string);
        LocaisJpaController controller = (LocaisJpaController) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "locaisJpa");
        return controller.findLocais(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Locais) {
            Locais o = (Locais) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: bd.Locais");
        }
    }

}
