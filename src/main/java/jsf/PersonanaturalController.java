package jsf;

import entities.Personanatural;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jpa.session.PersonanaturalFacade;

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
import javax.inject.Inject;

@Named("personanaturalController")
@SessionScoped
public class PersonanaturalController implements Serializable {

	@EJB
	private jpa.session.PersonanaturalFacade ejbFacade;
	private List<Personanatural> items = null;
	private Personanatural selected;
	@Inject
	private MunicipiosdaneController mDaneController;// Mio.
	
	public PersonanaturalController() {
	}

	public Personanatural getSelected() {
		return selected;
	}

	public void setSelected(Personanatural selected) {
		this.selected = selected;
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	private PersonanaturalFacade getFacade() {
		return ejbFacade;
	}

	public Personanatural prepareCreate() {
		selected = new Personanatural();
		mDaneController.setDepartamentosdaneDDSel(null);
		initializeEmbeddableKey();
		return selected;
	}

	public void prepararEdicion() {
		if(selected.getMunicipiosdane() != null)
			mDaneController.setDepartamentosdaneDDSel(selected.getMunicipiosdane().getDepartamentosdane());
		else
			mDaneController.setDepartamentosdaneDDSel(null);
	}
					
	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("resources/Bundle").getString("PersonanaturalCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("resources/Bundle").getString("PersonanaturalUpdated"));
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("resources/Bundle").getString("PersonanaturalDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Personanatural> getItems() {
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
					JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
			}
		}
	}

	public Personanatural getPersonanatural(java.lang.Integer id) {
		return getFacade().find(id);
	}

	public List<Personanatural> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Personanatural> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@FacesConverter(forClass = Personanatural.class)
	public static class PersonanaturalControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			PersonanaturalController controller = (PersonanaturalController) facesContext.getApplication().getELResolver().
							getValue(facesContext.getELContext(), null, "personanaturalController");
			return controller.getPersonanatural(getKey(value));
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
			if (object instanceof Personanatural) {
				Personanatural o = (Personanatural) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Personanatural.class.getName()});
				return null;
			}
		}

	}

}
