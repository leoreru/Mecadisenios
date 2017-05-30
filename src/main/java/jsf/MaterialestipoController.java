package jsf;

import entities.Materialescategoria;
import entities.Materialestipo;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jpa.session.MaterialestipoFacade;

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

@Named("materialestipoController")
@SessionScoped
public class MaterialestipoController implements Serializable {

	@EJB
	private jpa.session.MaterialestipoFacade ejbFacade;
	private List<Materialestipo> items = null;
	private Materialestipo selected;
	private Materialescategoria materialescategoriaDDSel; //Mio

	public MaterialestipoController() {
	}

	public Materialestipo getSelected() {
		return selected;
	}

	public void setSelected(Materialestipo selected) {
		this.selected = selected;
	}

	public List<Materialestipo> getItemsAvailableSelectOneByCategoria() {
		return getFacade().findByCategoria(materialescategoriaDDSel);
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	private MaterialestipoFacade getFacade() {
		return ejbFacade;
	}

	public Materialestipo prepareCreate() {
		selected = new Materialestipo();
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/resources/Bundle").getString("MaterialestipoCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/resources/Bundle").getString("MaterialestipoUpdated"));
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/resources/Bundle").getString("MaterialestipoDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Materialestipo> getItems() {
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

	public Materialestipo getMaterialestipo(java.lang.Integer id) {
		return getFacade().find(id);
	}

	public List<Materialestipo> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Materialestipo> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@FacesConverter(forClass = Materialestipo.class)
	public static class MaterialestipoControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			MaterialestipoController controller = (MaterialestipoController) facesContext.getApplication().getELResolver().
							getValue(facesContext.getELContext(), null, "materialestipoController");
			return controller.getMaterialestipo(getKey(value));
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
			if (object instanceof Materialestipo) {
				Materialestipo o = (Materialestipo) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Materialestipo.class.getName()});
				return null;
			}
		}

	}

	/**
	 * @return the materialescategoriaDDSel
	 */
	public Materialescategoria getMaterialescategoriaDDSel() {
		return materialescategoriaDDSel;
	}

	/**
	 * @param materialescategoriaDDSel the materialescategoriaDDSel to set
	 */
	public void setMaterialescategoriaDDSel(Materialescategoria materialescategoriaDDSel) {
		this.materialescategoriaDDSel = materialescategoriaDDSel;
	}

}
