package jsf;

import entities.Cuentas;
import entities.Giros;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jpa.session.GirosFacade;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
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
import javax.faces.validator.ValidatorException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import org.primefaces.component.selectonemenu.SelectOneMenu;

@Named("girosController")
@SessionScoped
public class GirosController implements Serializable {

	@EJB
	private jpa.session.GirosFacade ejbFacade;
	private List<Giros> items = null;
	private Giros selected;

	public GirosController() {
	}

	public void validarCuentas(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		UIInput cuentaOrigen;
						
		String msg = ResourceBundle.getBundle("/resources/Bundle").getString("ErrorCuentasGiro");
		if(((SelectOneMenu)component).getClientId().equals("GirosCreateForm:idCuentaDestino"))
			cuentaOrigen = (UIInput)context.getViewRoot().findComponent("GirosCreateForm:idCuentaOrigen");
		else //Edición
			cuentaOrigen = (UIInput)context.getViewRoot().findComponent("GirosEditForm:idCuentaOrigen");
		//String cuentaOrigenV = (String)cuentaOrigen.getSubmittedValue() 
		
		if(Objects.equals(((Cuentas)cuentaOrigen.getValue()).getId(), ((Cuentas)value).getId()))
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
	}

	public Giros getSelected() {
		return selected;
	}

	public void setSelected(Giros selected) {
		this.selected = selected;
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	private GirosFacade getFacade() {
		return ejbFacade;
	}

	public Giros prepareCreate() {
		selected = new Giros();
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/resources/Bundle").getString("GirosCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/resources/Bundle").getString("GirosUpdated"));
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/resources/Bundle").getString("GirosDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Giros> getItems() {
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

	public Giros getGiros(java.lang.Integer id) {
		return getFacade().find(id);
	}

	public List<Giros> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Giros> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@FacesConverter(forClass = Giros.class)
	public static class GirosControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			GirosController controller = (GirosController) facesContext.getApplication().getELResolver().
							getValue(facesContext.getELContext(), null, "girosController");
			return controller.getGiros(getKey(value));
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
			if (object instanceof Giros) {
				Giros o = (Giros) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Giros.class.getName()});
				return null;
			}
		}

	}

}
