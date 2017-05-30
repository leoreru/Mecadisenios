package jsf;

import entities.Otrosgastosproyecto;
import entities.Proyectos;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jpa.session.OtrosgastosproyectoFacade;

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

@Named("otrosgastosproyectoController")
@SessionScoped
public class OtrosgastosproyectoController implements Serializable {

	@EJB
	private jpa.session.OtrosgastosproyectoFacade ejbFacade;
	private List<Otrosgastosproyecto> items = null;
	private Otrosgastosproyecto selected;
	private Proyectos proyectoDDSel; //Mio
	private Proyectos proyectoDDSelAnt;

	public OtrosgastosproyectoController() {
	}

	public Otrosgastosproyecto getSelected() {
		return selected;
	}

	public void setSelected(Otrosgastosproyecto selected) {
		this.selected = selected;
	}

	protected void setEmbeddableKeys() {
		selected.getOtrosgastosproyectoPK().setIdTipoGasto(selected.getTipogasto().getId());
		selected.getOtrosgastosproyectoPK().setIdProyecto(selected.getProyectos().getId());
	}

	protected void initializeEmbeddableKey() {
		selected.setOtrosgastosproyectoPK(new entities.OtrosgastosproyectoPK());
	}

	private OtrosgastosproyectoFacade getFacade() {
		return ejbFacade;
	}

	public Otrosgastosproyecto prepareCreate() {
		selected = new Otrosgastosproyecto();
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/resources/Bundle").getString("OtrosgastosproyectoCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/resources/Bundle").getString("OtrosgastosproyectoUpdated"));
		//Mio(no quería actualizar la lista cuando se hacia una edición).
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/resources/Bundle").getString("OtrosgastosproyectoDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Otrosgastosproyecto> getItems() {
		if (items == null) {
			items = getFacade().findAll();
		}
		return items;
	}

	//Mio.
	public List<Otrosgastosproyecto> getItemsByProyecto() {
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

	public Otrosgastosproyecto getOtrosgastosproyecto(entities.OtrosgastosproyectoPK id) {
		return getFacade().find(id);
	}

	public List<Otrosgastosproyecto> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Otrosgastosproyecto> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@FacesConverter(forClass = Otrosgastosproyecto.class)
	public static class OtrosgastosproyectoControllerConverter implements Converter {

		private static final String SEPARATOR = "#";
		private static final String SEPARATOR_ESCAPED = "\\#";

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			OtrosgastosproyectoController controller = (OtrosgastosproyectoController) facesContext.getApplication().getELResolver().
							getValue(facesContext.getELContext(), null, "otrosgastosproyectoController");
			return controller.getOtrosgastosproyecto(getKey(value));
		}

		entities.OtrosgastosproyectoPK getKey(String value) {
			entities.OtrosgastosproyectoPK key;
			String values[] = value.split(SEPARATOR_ESCAPED);
			key = new entities.OtrosgastosproyectoPK();
			key.setIdProyecto(Integer.parseInt(values[0]));
			key.setIdTipoGasto(Short.parseShort(values[1]));
			return key;
		}

		String getStringKey(entities.OtrosgastosproyectoPK value) {
			StringBuilder sb = new StringBuilder();
			sb.append(value.getIdProyecto());
			sb.append(SEPARATOR);
			sb.append(value.getIdTipoGasto());
			return sb.toString();
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Otrosgastosproyecto) {
				Otrosgastosproyecto o = (Otrosgastosproyecto) object;
				return getStringKey(o.getOtrosgastosproyectoPK());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Otrosgastosproyecto.class.getName()});
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
