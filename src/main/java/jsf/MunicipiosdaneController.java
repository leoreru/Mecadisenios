package jsf;

import entities.Departamentosdane;
import entities.Municipiosdane;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jpa.session.MunicipiosdaneFacade;

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

@Named("municipiosdaneController")
@SessionScoped
public class MunicipiosdaneController implements Serializable {

	@EJB
	private jpa.session.MunicipiosdaneFacade ejbFacade;
	private List<Municipiosdane> items = null;
	private Municipiosdane selected;
	private Departamentosdane departamentosdaneDDSel; //Mio

	public MunicipiosdaneController() {
	}

	public Municipiosdane getSelected() {
		return selected;
	}

	public void setSelected(Municipiosdane selected) {
		this.selected = selected;
	}

	public List<Municipiosdane> getItemsAvailableSelectOneByDepartamento() {
		return getFacade().findByDepartamento(departamentosdaneDDSel);
	}
			
	protected void setEmbeddableKeys() {
		selected.getMunicipiosdanePK().setCodDep(selected.getDepartamentosdane().getCodDep());
	}

	protected void initializeEmbeddableKey() {
		selected.setMunicipiosdanePK(new entities.MunicipiosdanePK());
	}

	private MunicipiosdaneFacade getFacade() {
		return ejbFacade;
	}

	public Municipiosdane prepareCreate() {
		selected = new Municipiosdane();
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/resources/Bundle").getString("MunicipiosdaneCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/resources/Bundle").getString("MunicipiosdaneUpdated"));
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/resources/Bundle").getString("MunicipiosdaneDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Municipiosdane> getItems() {
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

	public Municipiosdane getMunicipiosdane(entities.MunicipiosdanePK id) {
		return getFacade().find(id);
	}

	public List<Municipiosdane> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Municipiosdane> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@FacesConverter(forClass = Municipiosdane.class)
	public static class MunicipiosdaneControllerConverter implements Converter {

		private static final String SEPARATOR = "#";
		private static final String SEPARATOR_ESCAPED = "\\#";

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			MunicipiosdaneController controller = (MunicipiosdaneController) facesContext.getApplication().getELResolver().
							getValue(facesContext.getELContext(), null, "municipiosdaneController");
			return controller.getMunicipiosdane(getKey(value));
		}

		entities.MunicipiosdanePK getKey(String value) {
			entities.MunicipiosdanePK key;
			String values[] = value.split(SEPARATOR_ESCAPED);
			key = new entities.MunicipiosdanePK();
			key.setCodDep(Short.parseShort(values[0]));
			key.setCodMun(Short.parseShort(values[1]));
			return key;
		}

		String getStringKey(entities.MunicipiosdanePK value) {
			StringBuilder sb = new StringBuilder();
			sb.append(value.getCodDep());
			sb.append(SEPARATOR);
			sb.append(value.getCodMun());
			return sb.toString();
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Municipiosdane) {
				Municipiosdane o = (Municipiosdane) object;
				return getStringKey(o.getMunicipiosdanePK());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Municipiosdane.class.getName()});
				return null;
			}
		}

	}

	/**
	 * @return the departamentosdaneDDSel
	 */
	public Departamentosdane getDepartamentosdaneDDSel() {
		return departamentosdaneDDSel;
	}

	/**
	 * @param departamentosdaneDDSel the departamentosdaneDDSel to set
	 */
	public void setDepartamentosdaneDDSel(Departamentosdane departamentosdaneDDSel) {
		this.departamentosdaneDDSel = departamentosdaneDDSel;
	}

}
