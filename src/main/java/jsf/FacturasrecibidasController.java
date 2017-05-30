package jsf;

import entities.Facturasrecibidas;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jpa.session.FacturasrecibidasFacade;

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

@Named("facturasrecibidasController")
@SessionScoped
public class FacturasrecibidasController implements Serializable {

	@EJB
	private jpa.session.FacturasrecibidasFacade ejbFacade;
	private List<Facturasrecibidas> items = null;
	private Facturasrecibidas selected;
	private boolean perNat;

	public FacturasrecibidasController() {
	}

	public Facturasrecibidas getSelected() {
		return selected;
	}

	public void setSelected(Facturasrecibidas selected) {
		this.selected = selected;
		if(selected != null)
			perNat = selected.getIdProveedorPerNat() != null;
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	private FacturasrecibidasFacade getFacade() {
		return ejbFacade;
	}

	public Facturasrecibidas prepareCreate() {
		selected = new Facturasrecibidas();
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/resources/Bundle").getString("FacturasrecibidasCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/resources/Bundle").getString("FacturasrecibidasUpdated"));
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/resources/Bundle").getString("FacturasrecibidasDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Facturasrecibidas> getItems() {
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
						selected.setIdProveedorPerJur(null);
					else
						selected.setIdProveedorPerNat(null);
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

	public Facturasrecibidas getFacturas(java.lang.Integer id) {
		return getFacade().find(id);
	}

	public List<Facturasrecibidas> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Facturasrecibidas> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@FacesConverter(forClass = Facturasrecibidas.class)
	public static class FacturasControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			FacturasrecibidasController controller = (FacturasrecibidasController) facesContext.getApplication().getELResolver().
							getValue(facesContext.getELContext(), null, "facturasrecibidasController");
			return controller.getFacturas(getKey(value));
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
			if (object instanceof Facturasrecibidas) {
				Facturasrecibidas o = (Facturasrecibidas) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Facturasrecibidas.class.getName()});
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
