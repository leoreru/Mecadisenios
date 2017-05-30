package jsf;

import entities.Contactoscliente;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jpa.session.ContactosclienteFacade;

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

@Named("contactosclienteController")
@SessionScoped
public class ContactosclienteController implements Serializable {

	@EJB
	private jpa.session.ContactosclienteFacade ejbFacade;
	private List<Contactoscliente> items = null;
	private Contactoscliente selected;
	private boolean perNat;

	public ContactosclienteController() {
	}

	public Contactoscliente getSelected() {
		return selected;
	}

	public void setSelected(Contactoscliente selected) {
		this.selected = selected;
		if(selected != null)
			perNat = selected.getIdClientePerNat() != null;
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	private ContactosclienteFacade getFacade() {
		return ejbFacade;
	}

	public Contactoscliente prepareCreate() {
		selected = new Contactoscliente();
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("resources/Bundle").getString("ContactosclienteCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("resources/Bundle").getString("ContactosclienteUpdated"));
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("resources/Bundle").getString("ContactosclienteDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Contactoscliente> getItems() {
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
						selected.setIdClientePerJur(null);
					else
						selected.setIdClientePerNat(null);
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
					JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
			}
		}
	}

	public Contactoscliente getContactoscliente(java.lang.Integer id) {
		return getFacade().find(id);
	}

	public List<Contactoscliente> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Contactoscliente> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@FacesConverter(forClass = Contactoscliente.class)
	public static class ContactosclienteControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			ContactosclienteController controller = (ContactosclienteController) facesContext.getApplication().getELResolver().
							getValue(facesContext.getELContext(), null, "contactosclienteController");
			return controller.getContactoscliente(getKey(value));
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
			if (object instanceof Contactoscliente) {
				Contactoscliente o = (Contactoscliente) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Contactoscliente.class.getName()});
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
