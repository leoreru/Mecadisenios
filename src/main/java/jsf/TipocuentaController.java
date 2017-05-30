package jsf;

import entities.Tipocuenta;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jpa.session.TipocuentaFacade;

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

@Named("tipocuentaController")
@SessionScoped
public class TipocuentaController implements Serializable {

	@EJB
	private jpa.session.TipocuentaFacade ejbFacade;
	private List<Tipocuenta> items = null;
	private Tipocuenta selected;

	public TipocuentaController() {
	}

	public Tipocuenta getSelected() {
		return selected;
	}

	public void setSelected(Tipocuenta selected) {
		this.selected = selected;
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	private TipocuentaFacade getFacade() {
		return ejbFacade;
	}

	public Tipocuenta prepareCreate() {
		selected = new Tipocuenta();
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/resources/Bundle").getString("TipocuentaCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/resources/Bundle").getString("TipocuentaUpdated"));
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/resources/Bundle").getString("TipocuentaDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Tipocuenta> getItems() {
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

	public Tipocuenta getTipocuenta(java.lang.Short id) {
		return getFacade().find(id);
	}

	public List<Tipocuenta> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Tipocuenta> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@FacesConverter(forClass = Tipocuenta.class)
	public static class TipocuentaControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			TipocuentaController controller = (TipocuentaController) facesContext.getApplication().getELResolver().
							getValue(facesContext.getELContext(), null, "tipocuentaController");
			return controller.getTipocuenta(getKey(value));
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
			if (object instanceof Tipocuenta) {
				Tipocuenta o = (Tipocuenta) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Tipocuenta.class.getName()});
				return null;
			}
		}

	}

}
