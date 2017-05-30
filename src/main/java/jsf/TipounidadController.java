package jsf;

import entities.Tipounidad;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jpa.session.TipounidadFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("tipounidadController")
@SessionScoped
public class TipounidadController implements Serializable {

	@EJB
	private jpa.session.TipounidadFacade ejbFacade;
	private List<Tipounidad> items = null;
	private Tipounidad selected;

	public TipounidadController() {
	}

	public Tipounidad getSelected() {
		return selected;
	}

	public void setSelected(Tipounidad selected) {
		this.selected = selected;
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	private TipounidadFacade getFacade() {
		return ejbFacade;
	}

	public Tipounidad prepareCreate() {
		selected = new Tipounidad();
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/resources/Bundle").getString("TipounidadCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/resources/Bundle").getString("TipounidadUpdated"));
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/resources/Bundle").getString("TipounidadDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Tipounidad> getItems() {
		if (items == null) {
			items = getFacade().findAll();
		}
		return items;
	}

	private void persist(PersistAction persistAction, String successMessage) {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				switch (persistAction) {
					case CREATE:
						getFacade().create(selected);
						break;
					case UPDATE:
						getFacade().edit(selected);
						break;
					case DELETE:
						getFacade().remove(selected);
						break;
				}

				/*
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
				 */
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
					JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
			}
		}
	}

	public Tipounidad getTipounidad(java.lang.Short id) {
		return getFacade().find(id);
	}

	public List<Tipounidad> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Tipounidad> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@FacesConverter(forClass = Tipounidad.class)
	public static class TipounidadControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			TipounidadController controller = (TipounidadController) facesContext.getApplication().getELResolver().
							getValue(facesContext.getELContext(), null, "tipounidadController");
			return controller.getTipounidad(getKey(value));
		}

		java.lang.Short getKey(String value) {
			java.lang.Short key;
			key = Short.valueOf(value);
			return key;
		}

		String getStringKey(java.lang.Short value) {
			StringBuilder sb = new StringBuilder();
			sb.append(value);
			return sb.toString();
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Tipounidad) {
				Tipounidad o = (Tipounidad) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Tipounidad.class.getName()});
				return null;
			}
		}

	}

}
