package jsf;

import entities.Detallefacturareci;
import entities.Facturasrecibidas;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jpa.session.DetallefacturareciFacade;

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

@Named("detallefacturareciController")
@SessionScoped
public class DetallefacturareciController implements Serializable {

	@EJB
	private jpa.session.DetallefacturareciFacade ejbFacade;
	private List<Detallefacturareci> items = null;
	private Detallefacturareci selected;
	private Facturasrecibidas facturareciDDSel; //Mio
	private Facturasrecibidas facturareciDDSelAnt;
	// Mio, Para poner la categoría del registro a editar, en la variable
	// correspondiente del controlador MaterialesController que se usará en el
	// dialogo edit.
	@Inject
	private MaterialesController matContr;
	
	public DetallefacturareciController() {
	}

	public Detallefacturareci getSelected() {
		return selected;
	}

	public void setSelected(Detallefacturareci selected) {
		this.selected = selected;
	}

	protected void setEmbeddableKeys() {
		selected.getDetallefacturareciPK().setIdFacturaReci(selected.getFacturasrecibidas().getId());
		selected.getDetallefacturareciPK().setIdMaterial(selected.getMateriales().getId());
	}

	protected void initializeEmbeddableKey() {
		selected.setDetallefacturareciPK(new entities.DetallefacturareciPK());
	}

	private DetallefacturareciFacade getFacade() {
		return ejbFacade;
	}

	public Detallefacturareci prepareCreate() {
		selected = new Detallefacturareci();
		initializeEmbeddableKey();
		return selected;
	}
	//Mio.
	public void prepareEdit() {
		matContr.setMaterialescategoriaDDSel(selected.getMateriales().getIdMaterialTipo().getIdCategoria());
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("resources/Bundle").getString("DetallefacturareciCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("resources/Bundle").getString("DetallefacturareciUpdated"));
		//Mio(no quería actualizar la lista cuando se hacia una edición).
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("resources/Bundle").getString("DetallefacturareciDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Detallefacturareci> getItems() {
		if (items == null) {
			items = getFacade().findAll();
		}
		return items;
	}

	//Mio.
	public List<Detallefacturareci> getItemsByFacturareci() {
		if (items == null || facturareciDDSelAnt != facturareciDDSel) {
			facturareciDDSelAnt = facturareciDDSel;
			items = getFacade().findByFacturareci(facturareciDDSel);
		}
		return items;
	}

	private void persist(PersistAction persistAction, String successMessage) {
		if (selected != null) {
			// Tiene que estar antes de setEmbeddableKeys() por que allí se llena la
			// clave principal compuesta(esta tiene el id de la factura.)
			if(persistAction==PersistAction.CREATE) {
				// El registro nuevo se hace pertenecer a la factura elegida en la lista
				// principal.
				selected.setFacturasrecibidas(facturareciDDSel);
			}
			if(persistAction==PersistAction.UPDATE) {
				// Esto es necesario ya que el usuario puede cambiar la clave principal
				// compuesta del registro(Si no se hace esto JPA crea un nuevo registro).
				// Notese que está antes de setEmbeddableKeys().
				getFacade().remove(selected);
			}
			setEmbeddableKeys();
			try {
				switch(persistAction) {
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

	public Detallefacturareci getDetallefacturareci(entities.DetallefacturareciPK id) {
		return getFacade().find(id);
	}

	public List<Detallefacturareci> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Detallefacturareci> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@FacesConverter(forClass = Detallefacturareci.class)
	public static class DetallefacturareciControllerConverter implements Converter {

		private static final String SEPARATOR = "#";
		private static final String SEPARATOR_ESCAPED = "\\#";

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			DetallefacturareciController controller = (DetallefacturareciController) facesContext.getApplication().getELResolver().
							getValue(facesContext.getELContext(), null, "detallefacturareciController");
			return controller.getDetallefacturareci(getKey(value));
		}

		entities.DetallefacturareciPK getKey(String value) {
			entities.DetallefacturareciPK key;
			String values[] = value.split(SEPARATOR_ESCAPED);
			key = new entities.DetallefacturareciPK();
			key.setIdFacturaReci(Integer.parseInt(values[0]));
			key.setIdMaterial(Integer.parseInt(values[1]));
			return key;
		}

		String getStringKey(entities.DetallefacturareciPK value) {
			StringBuilder sb = new StringBuilder();
			sb.append(value.getIdFacturaReci());
			sb.append(SEPARATOR);
			sb.append(value.getIdMaterial());
			return sb.toString();
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Detallefacturareci) {
				Detallefacturareci o = (Detallefacturareci) object;
				return getStringKey(o.getDetallefacturareciPK());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Detallefacturareci.class.getName()});
				return null;
			}
		}

	}

	/**
	 * @return the facturareciDDSel
	 */
	public Facturasrecibidas getFacturareciDDSel() {
		return facturareciDDSel;
	}

	/**
	 * @param facturareciDDSel the facturareciDDSel to set
	 */
	public void setFacturareciDDSel(Facturasrecibidas facturareciDDSel) {
		this.facturareciDDSel = facturareciDDSel;
	}

}
