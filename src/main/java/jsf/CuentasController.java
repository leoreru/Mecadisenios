package jsf;

import entities.Cuentas;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jpa.session.CuentasFacade;

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

@Named("cuentasController")
@SessionScoped
public class CuentasController implements Serializable {

	@EJB
	private jpa.session.CuentasFacade ejbFacade;
	private List<Cuentas> items = null;
	private Cuentas selected;
	private boolean perNat;

	public CuentasController() {
	}

	public Cuentas getSelected() {
		return selected;
	}

	public void setSelected(Cuentas selected) {
		this.selected = selected;
		if (selected != null) {
			perNat = selected.getIdTitularPerNat() != null;
		}
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	private CuentasFacade getFacade() {
		return ejbFacade;
	}

	public Cuentas prepareCreate() {
		selected = new Cuentas();
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/resources/Bundle").getString("CuentasCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/resources/Bundle").getString("CuentasUpdated"));
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/resources/Bundle").getString("CuentasDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Cuentas> getItems() {
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
					//Mio. El proyecto puede tener al cliente como una persona natural o como
					// una persona juridica, pero no ambas.
					if(perNat)
						selected.setIdTitularPerJur(null);
					else
						selected.setIdTitularPerNat(null);

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
					JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
			}
		}
	}

	public Cuentas getCuentas(java.lang.Short id) {
		return getFacade().find(id);
	}

	public List<Cuentas> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Cuentas> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@FacesConverter(forClass = Cuentas.class)
	public static class CuentasControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			CuentasController controller = (CuentasController) facesContext.getApplication().getELResolver().
							getValue(facesContext.getELContext(), null, "cuentasController");
			return controller.getCuentas(getKey(value));
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
			if (object instanceof Cuentas) {
				Cuentas o = (Cuentas) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cuentas.class.getName()});
				return null;
			}
		}

	}

	/**
	 * @return the perNat
	 */
	public boolean isPerNat() {
		return perNat;
	}

	/**
	 * @param perNat the perNat to set
	 */
	public void setPerNat(boolean perNat) {
		this.perNat = perNat;
	}

}
