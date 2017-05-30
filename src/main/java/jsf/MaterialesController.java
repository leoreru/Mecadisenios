package jsf;

import entities.Materiales;
import entities.Materialescategoria;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jpa.session.MaterialesFacade;

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

@Named("materialesController")
@SessionScoped
public class MaterialesController implements Serializable {

	@EJB
	private jpa.session.MaterialesFacade ejbFacade;
	private List<Materiales> items = null;
	private Materiales selected;
	private Materialescategoria materialescategoriaDDSel; //Mio
	private Materialescategoria materialescategoriaDDSelAnt;
	// Mio, Para poner la categoría elegida, en la lista de materiales, en la variable
	// correspondiente del controlador MaterialestipoController que se usará en el
	// dialogo create de la tabla materiales.
	@Inject
	private MaterialestipoController matTipContr;

	public MaterialesController() {
	}

	public Materiales getSelected() {
		return selected;
	}

	public void setSelected(Materiales selected) {
		this.selected = selected;
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	private MaterialesFacade getFacade() {
		return ejbFacade;
	}

	public Materiales prepareCreate() {
		selected = new Materiales();
		matTipContr.setMaterialescategoriaDDSel(materialescategoriaDDSel);
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/resources/Bundle").getString("MaterialesCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/resources/Bundle").getString("MaterialesUpdated"));
		//Mio(no quería actualizar la lista cuando se hacia una edición).
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/resources/Bundle").getString("MaterialesDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Materiales> getItems() {
		if (items == null) {
			items = getFacade().findAll();
		}
		return items;
	}

	//Mio.
	public List<Materiales> getItemsByCategoria() {
		if (items == null || materialescategoriaDDSelAnt != materialescategoriaDDSel) {
			materialescategoriaDDSelAnt = materialescategoriaDDSel;
			items = getFacade().findByCategoria(materialescategoriaDDSel);
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

	public Materiales getMateriales(java.lang.Integer id) {
		return getFacade().find(id);
	}

	public List<Materiales> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Materiales> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}
	//Mio.

	public List<Materiales> getItemsAvailableSelectOneByCategoria() {
		return getFacade().findByCategoria(materialescategoriaDDSel);
	}

	/*
		//Mio
		public List<Materiales> getItemsAvailableSelectOne(Materialescategoria MC) {
        return getFacade().findAll();
    }
	 */
	@FacesConverter(forClass = Materiales.class)
	public static class MaterialesControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			MaterialesController controller = (MaterialesController) facesContext.getApplication().getELResolver().
							getValue(facesContext.getELContext(), null, "materialesController");
			return controller.getMateriales(getKey(value));
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
			if (object instanceof Materiales) {
				Materiales o = (Materiales) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Materiales.class.getName()});
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
