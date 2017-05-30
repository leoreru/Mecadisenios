package jsf;

import entities.Materialesproyecto;
import entities.Proyectos;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jpa.session.MaterialesproyectoFacade;

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
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

@Named("materialesproyectoController")
@SessionScoped
public class MaterialesproyectoController implements Serializable {

	@EJB
	private jpa.session.MaterialesproyectoFacade ejbFacade;
	private List<Materialesproyecto> items = null;
	private Materialesproyecto selected;
	private Proyectos proyectoDDSel; //Mio
	private Proyectos proyectoDDSelAnt;
	// Mio, Para poner la categoría del registro a editar, en la variable
	// correspondiente del controlador MaterialesController que se usará en el
	// dialogo edit.
	@Inject
	private MaterialesController matContr;
	
	public MaterialesproyectoController() {
	}

	public Materialesproyecto getSelected() {

		return selected;
	}

	public void setSelected(Materialesproyecto selected) {
		this.selected = selected;
	}
	
	public void materialSelectionChanged(final AjaxBehaviorEvent event)  {
		// Para actualizar el campo ValorCotizado con el valor que está en la tabla
		// de materiales:
		if(selected != null)
			if(selected.getMateriales() != null)
				selected.setValorCotizado(selected.getMateriales().getValor());
	}
	
	public void categoriaSelectionChanged(final AjaxBehaviorEvent event)  {
		// Cuando se cambia la categoría, y se llega aquí, selected.materiales no
		// ha sido actualizado aun, por eso tuve que hacer esto(si se le ocurre algo
		// mejor hagalo).
		if(selected != null)
			selected.setValorCotizado(null);
	}

	protected void setEmbeddableKeys() {
		selected.getMaterialesproyectoPK().setIdProyecto(selected.getProyectos().getId());
		selected.getMaterialesproyectoPK().setIdMaterial(selected.getMateriales().getId());
	}

	protected void initializeEmbeddableKey() {
		selected.setMaterialesproyectoPK(new entities.MaterialesproyectoPK());
	}

	private MaterialesproyectoFacade getFacade() {
		return ejbFacade;
	}

	public Materialesproyecto prepareCreate() {
		selected = new Materialesproyecto();
		initializeEmbeddableKey();
		return selected;
	}
	//Mio.
	public void prepareEdit() {
		matContr.setMaterialescategoriaDDSel(selected.getMateriales().getIdMaterialTipo().getIdCategoria());
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/resources/Bundle").getString("MaterialesproyectoCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/resources/Bundle").getString("MaterialesproyectoUpdated"));
		//Mio(no quería actualizar la lista cuando se hacia una edición).
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/resources/Bundle").getString("MaterialesproyectoDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Materialesproyecto> getItems() {
		if (items == null) {
			items = getFacade().findAll();
		}
		return items;
	}
	
	//Mio.
	public List<Materialesproyecto> getItemsByProyecto() {
		if (items == null || proyectoDDSelAnt != proyectoDDSel) {
			proyectoDDSelAnt = proyectoDDSel;
			items = getFacade().findByProyecto(proyectoDDSel);
		}
		return items;
	}
	
	private void persist(PersistAction persistAction, String successMessage) {
		if (selected != null) {
			if(persistAction == PersistAction.CREATE){
				// Se establece el proyecto como el proyecto elegido en el filtro
				// de la vista de lista.
				selected.setProyectos(proyectoDDSel);
			}
			if(persistAction==PersistAction.UPDATE) {
				// Esto es necesario ya que el usuario puede cambiar la clave principal
				// compuesta del registro(Si no se hace esto JPA crea un nuevo registro).
				// Notese que está antes de setEmbeddableKeys().
				getFacade().remove(selected);
			}
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

	public Materialesproyecto getMaterialesproyecto(entities.MaterialesproyectoPK id) {
		return getFacade().find(id);
	}

	public List<Materialesproyecto> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Materialesproyecto> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@FacesConverter(forClass = Materialesproyecto.class)
	public static class MaterialesproyectoControllerConverter implements Converter {

		private static final String SEPARATOR = "#";
		private static final String SEPARATOR_ESCAPED = "\\#";

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			MaterialesproyectoController controller = (MaterialesproyectoController) facesContext.getApplication().getELResolver().
							getValue(facesContext.getELContext(), null, "materialesproyectoController");
			return controller.getMaterialesproyecto(getKey(value));
		}

		entities.MaterialesproyectoPK getKey(String value) {
			entities.MaterialesproyectoPK key;
			String values[] = value.split(SEPARATOR_ESCAPED);
			key = new entities.MaterialesproyectoPK();
			key.setIdProyecto(Integer.parseInt(values[0]));
			key.setIdMaterial(Integer.parseInt(values[1]));
			return key;
		}

		String getStringKey(entities.MaterialesproyectoPK value) {
			StringBuilder sb = new StringBuilder();
			sb.append(value.getIdProyecto());
			sb.append(SEPARATOR);
			sb.append(value.getIdMaterial());
			return sb.toString();
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Materialesproyecto) {
				Materialesproyecto o = (Materialesproyecto) object;
				return getStringKey(o.getMaterialesproyectoPK());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Materialesproyecto.class.getName()});
				return null;
			}
		}

	}

	/**
	 * @return the proyectoDDSel
	 */
	public Proyectos getProyectoDDSel() {
		return proyectoDDSel;
	}

	/**
	 * @param proyectoDDSel the proyectoDDSel to set
	 */
	public void setProyectoDDSel(Proyectos proyectoDDSel) {
		this.proyectoDDSel = proyectoDDSel;
	}
}
