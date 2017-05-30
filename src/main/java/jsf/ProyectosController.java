package jsf;

import entities.Personajuridica;
import entities.Personanatural;
import entities.Proyectos;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jpa.session.ProyectosFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

@Named("proyectosController")
@SessionScoped
public class ProyectosController implements Serializable {

	@EJB
	private jpa.session.ProyectosFacade ejbFacade;
	private List<Proyectos> items = null;
	private Proyectos selected;
	private boolean perNat;

	public ProyectosController() {
	}

	public Proyectos getSelected() {
		return selected;
	}

	public void setSelected(Proyectos selected) {
		this.selected = selected;
		if (selected != null) {
			perNat = selected.getIdClientePerNat() != null;
		}
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	private ProyectosFacade getFacade() {
		return ejbFacade;
	}

	public Proyectos prepareCreate() {
		selected = new Proyectos();
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/resources/Bundle").getString("ProyectosCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/resources/Bundle").getString("ProyectosUpdated"));
	}

	//@requestmapping
	public void genFactura() {
		List<Object[]> valoresInforme;
		Map<String, Double> mapValoresInforme = new HashMap<>();
		int i;
		String clave = "";

		//Llamar el procedimiento almacenado SP_GenerarFactura
		valoresInforme = getFacade().SP_GenerarFactura(selected);
		//Se asume que hay un numero par de datos y que el arreglo de arreglos 
		// valoresInforme solo tiene un arreglo(fila):
		i = 0;
		for (Object ob : valoresInforme.get(0)) {
			if (i % 2 == 0) {
				clave = (String) ob;
			} else {
				mapValoresInforme.put(clave, (Double) ob);
			}
			++i;
		}
		//selected.setFacturasgeneradas(getFacade().SP_GenerarFactura(selected));
		//getFacade().refrescarFacGen(selected.getFacturasgeneradas());
		FacesContext facesC = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesC.getExternalContext().getResponse();

		response.reset();
		response.setContentType("application/zip");
		response.addHeader("Content-Disposition", "attachment; filename="
						+ "CotizacionFac"
						+ selected.getId()
						+ ".zip");
		// Se crean los dos pdfs y se comprimen.
		try {
			ByteArrayOutputStream fos;
			boolean falloGenPdf1=false,falloGenPdf2=false;
			ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());

			getFacade().actualizarYLlenar(selected);
			//getFacade().llenarCollectionContactos(selected);
			ZipEntry ze = new ZipEntry("CotizacionFac" + selected.getId() + ".pdf");
			zos.putNextEntry(ze);
			fos = genPdf(null);
			if(fos.size()==0) falloGenPdf1 = true;
			zos.write(fos.toByteArray());
			ze = new ZipEntry("EstructuraDeCostoCotizacionFac" + selected.getId() + ".pdf");
			zos.putNextEntry(ze);
			fos = genPdf(mapValoresInforme);
			if(fos.size()==0) falloGenPdf2 = true;
			zos.write(fos.toByteArray());
			zos.closeEntry();
//			_pdfDocument.save(response.getOutputStream());
/*
			if(falloGenPdf1 || falloGenPdf2) {
				Exception ex = new Exception(ResourceBundle.getBundle("/resources/Bundle").getString("errorGenZipPdfs"));
				//Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/resources/Bundle").getString("errorGenZipPdfs"));
			}
*/
			if(!falloGenPdf1 || !falloGenPdf2) {
				zos.close();
				facesC.responseComplete();
			}
			else response.reset();
		} catch (Exception ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
			JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/resources/Bundle").getString("errorGenZipPdfs"));
		}

	}

	public ByteArrayOutputStream genPdf(Map<String, Double> mapValoresInforme) {
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		Locale locale = new Locale("es", "CO");
		NumberFormat FormatPesos = NumberFormat.getCurrencyInstance(locale);
		FormatPesos.setMaximumFractionDigits(0);
		String originalPdf;

		//HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		if (mapValoresInforme == null) {
			originalPdf = "resources/PlantillaFactura.pdf";
		} else {
			originalPdf = "resources/PlantillaEstructuraDeCosto.pdf";
		}
		//String targetPdf = "resources/PlantillaFacturaLlena.pdf";

		try {
			Calendar cal = Calendar.getInstance();
			InputStream inputStream = getClass().getClassLoader()
							.getResourceAsStream(originalPdf);
			Personajuridica persJur;
			Personanatural persNat, contactoPersNat;

			//URL url = new URL(originalPdf);
			//BufferedInputStream file = new BufferedInputStream(url.openStream());
			PDDocument _pdfDocument = PDDocument.load(inputStream);
			//PDDocument _pdfDocument = PDDocument.load(originalPdf.getBytes());
			//establecerCampo(_pdfDocument, "fecha", "9 de marzo de 2017");
			PDDocumentCatalog docCatalog = _pdfDocument.getDocumentCatalog();
			PDAcroForm acroForm = docCatalog.getAcroForm();
			PDField field = acroForm.getField("numCotizacion");
			if (field != null) {
				cal.setTime(selected.getFacturasgeneradas().getFecha());
				field.setValue(
								Integer.toString(cal.get(Calendar.YEAR))
								+ '-' + selected.getId());
			}
			field = acroForm.getField("fecha");
			if (field != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new Locale("es"));
				field.setValue(sdf.format(selected.getFacturasgeneradas().getFecha()));
			}
			persJur = selected.getIdClientePerJur();
			persNat = selected.getIdClientePerNat();
			field = acroForm.getField("nomCliente");
			if (field != null) {
				if (persJur == null) {
					if (persNat != null) {
						field.setValue(persNat.getNombres() + ' ' + persNat.getApellido1() + ' ' + persNat.getApellido2());
					}
				} else {
					field.setValue(persJur.getNombre());
				}
			}
			field = acroForm.getField("nomContactoCliente");
			if (field != null) {
				contactoPersNat = null;
				if (persJur == null) {
					if (persNat != null) {
						if (!persNat.getContactosclienteCollection().isEmpty()) {
							// Se toma el primer contacto en la colección.
							contactoPersNat = persNat.getContactosclienteCollection().iterator().next().getIdContactoPerNat();
						}
					}
				} else {
					if (!persJur.getContactosclienteCollection().isEmpty()) {
						// Se toma el primer contacto en la colección.
						contactoPersNat = persJur.getContactosclienteCollection().iterator().next().getIdContactoPerNat();
					}
				}
				if (contactoPersNat != null) {
					field.setValue("Atn: "
									+ contactoPersNat.getNombres()
									+ ' ' + contactoPersNat.getApellido1()
									+ (contactoPersNat.getApellido2() != null ? (' ' + contactoPersNat.getApellido2()) : ""));
				} else {
					field.setValue("");
				}
			}

			field = acroForm.getField("ciudadDep");
			if (field != null) {
				if (persJur == null) {
					if (persNat != null) {
						if (persNat.getMunicipiosdane() != null) {
							field.setValue(persNat.getMunicipiosdane().getNombre() + '-' + persNat.getMunicipiosdane().getDepartamentosdane().getNombre());
						}
					}
				} else {
					if (persJur.getMunicipiosdane() != null) {
						field.setValue(persJur.getMunicipiosdane().getNombre() + '-' + persJur.getMunicipiosdane().getDepartamentosdane().getNombre());
					}
				}
			}

			field = acroForm.getField("nomProyecto");
			if (field != null) {
				field.setValue(selected.getDescripcion());
			}
			field = acroForm.getField("cantidadProy");
			if (field != null) {
				field.setValue("1");//No se de donde sacar este dato.
			}
			field = acroForm.getField("valor");
			if (field != null) {
				field.setValue(FormatPesos.format(selected.getFacturasgeneradas().getValorSinIva()));
			}
			field = acroForm.getField("valAntesIva");// Igual al campo 'valor'.
			if (field != null) {
				field.setValue(FormatPesos.format(selected.getFacturasgeneradas().getValorSinIva()));
			}
			field = acroForm.getField("valIva");
			if (field != null) {
				field.setValue(FormatPesos.format(selected.getFacturasgeneradas().getValorSinIva().multiply(
								new BigDecimal(selected.getFacturasgeneradas().getPorcIva() / 100))));
			}
			field = acroForm.getField("valIvaIncluido");
			if (field != null) {
				field.setValue(FormatPesos.format(selected.getFacturasgeneradas().getValorSinIva().multiply(
								new BigDecimal(1 + selected.getFacturasgeneradas().getPorcIva() / 100))));
			}

			if (mapValoresInforme != null) {
				field = acroForm.getField("costoTot");
				if (field != null) {
					double costoTot;

					costoTot = selected.getFacturasgeneradas().getValorSinIva().doubleValue()
									- mapValoresInforme.get("valComision")
									- mapValoresInforme.get("valUtilidad")
									- mapValoresInforme.get("valAdmin");
					field.setValue(FormatPesos.format(costoTot));
				}
				field = acroForm.getField("valComision");
				if (field != null) {
					field.setValue(FormatPesos.format(mapValoresInforme.get("valComision")));
				}
				field = acroForm.getField("valUtilidad");
				if (field != null) {
					field.setValue(FormatPesos.format(mapValoresInforme.get("valUtilidad")));
				}
				field = acroForm.getField("valAdmin");
				if (field != null) {
					field.setValue(FormatPesos.format(mapValoresInforme.get("valAdmin")));
				}
			}
			_pdfDocument.save(fos);
			_pdfDocument.close();
		} catch (Exception ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
			JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/resources/Bundle").getString("errorGenPdf"));
		}
		return fos;
	}

	/*
	private void establecerCampo(PDDocument _pdfD, String name, String value) throws IOException {
		PDDocumentCatalog docCatalog = _pdfD.getDocumentCatalog();
		PDAcroForm acroForm = docCatalog.getAcroForm();
		PDField field = acroForm.getField( name );
		if( field != null ) {
			field.setValue(value);
		}
		else {
			//System.err.println( "No field found with name:" + name );
		}
	}
	 */
	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/resources/Bundle").getString("ProyectosDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Proyectos> getItems() {
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
					if (perNat) {
						selected.setIdClientePerJur(null);
					} else {
						selected.setIdClientePerNat(null);
					}
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

	public Proyectos getProyectos(java.lang.Integer id) {
		return getFacade().find(id);
	}

	public List<Proyectos> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Proyectos> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@FacesConverter(forClass = Proyectos.class)
	public static class ProyectosControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			ProyectosController controller = (ProyectosController) facesContext.getApplication().getELResolver().
							getValue(facesContext.getELContext(), null, "proyectosController");
			return controller.getProyectos(getKey(value));
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
			if (object instanceof Proyectos) {
				Proyectos o = (Proyectos) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Proyectos.class.getName()});
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
