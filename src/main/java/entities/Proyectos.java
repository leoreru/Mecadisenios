/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PC-HP
 */
@Entity
@Table(name = "proyectos")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Proyectos.findAll", query = "SELECT p FROM Proyectos p")
	, @NamedQuery(name = "Proyectos.findById", query = "SELECT p FROM Proyectos p WHERE p.id = :id")
	, @NamedQuery(name = "Proyectos.findByNombre", query = "SELECT p FROM Proyectos p WHERE p.nombre = :nombre")
	, @NamedQuery(name = "Proyectos.findByDescripcion", query = "SELECT p FROM Proyectos p WHERE p.descripcion = :descripcion")
	, @NamedQuery(name = "Proyectos.findByPorcAdmin", query = "SELECT p FROM Proyectos p WHERE p.porcAdmin = :porcAdmin")
	, @NamedQuery(name = "Proyectos.findByPorcComision", query = "SELECT p FROM Proyectos p WHERE p.porcComision = :porcComision")
	, @NamedQuery(name = "Proyectos.findByPorcUtilidad", query = "SELECT p FROM Proyectos p WHERE p.porcUtilidad = :porcUtilidad")})
public class Proyectos implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "nombre")
	private String nombre;
	@Size(max = 255)
	@Column(name = "descripcion")
	private String descripcion;
	@Basic(optional = false)
  @NotNull
  @Column(name = "porcAdmin")
	private float porcAdmin;
	@Basic(optional = false)
  @NotNull
  @Column(name = "porcComision")
	private float porcComision;
	@Basic(optional = false)
  @NotNull
  @Column(name = "porcUtilidad")
	private float porcUtilidad;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idProyecto")
	private Collection<Facturasrecibidas> facturasCollection;
	@OneToMany(mappedBy = "idProyecto")
	private Collection<Giros> girosCollection;
	@JoinColumn(name = "idClientePerJur", referencedColumnName = "id")
	@ManyToOne
	private Personajuridica idClientePerJur;
	@JoinColumn(name = "idClientePerNat", referencedColumnName = "id")
	@ManyToOne
	private Personanatural idClientePerNat;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectos")
	private Collection<Materialesproyecto> materialesproyectoCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectos")
	private Collection<Otrosgastosproyecto> otrosgastosproyectoCollection;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "proyectos")
	private Facturasgeneradas facturasgeneradas;

	public Proyectos() {
	}

	public Proyectos(Integer id) {
		this.id = id;
	}

	public Proyectos(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@XmlTransient
	public Collection<Facturasrecibidas> getFacturasCollection() {
		return facturasCollection;
	}

	public void setFacturasCollection(Collection<Facturasrecibidas> facturasCollection) {
		this.facturasCollection = facturasCollection;
	}

	@XmlTransient
	public Collection<Giros> getGirosCollection() {
		return girosCollection;
	}

	public void setGirosCollection(Collection<Giros> girosCollection) {
		this.girosCollection = girosCollection;
	}

	public Personajuridica getIdClientePerJur() {
		return idClientePerJur;
	}

	public void setIdClientePerJur(Personajuridica idClientePerJur) {
		this.idClientePerJur = idClientePerJur;
	}

	public Personanatural getIdClientePerNat() {
		return idClientePerNat;
	}

	public void setIdClientePerNat(Personanatural idClientePerNat) {
		this.idClientePerNat = idClientePerNat;
	}

	@XmlTransient
	public Collection<Materialesproyecto> getMaterialesproyectoCollection() {
		return materialesproyectoCollection;
	}

	public void setMaterialesproyectoCollection(Collection<Materialesproyecto> materialesproyectoCollection) {
		this.materialesproyectoCollection = materialesproyectoCollection;
	}

	@XmlTransient
	public Collection<Otrosgastosproyecto> getOtrosgastosproyectoCollection() {
		return otrosgastosproyectoCollection;
	}

	public void setOtrosgastosproyectoCollection(Collection<Otrosgastosproyecto> otrosgastosproyectoCollection) {
		this.otrosgastosproyectoCollection = otrosgastosproyectoCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Proyectos)) {
			return false;
		}
		Proyectos other = (Proyectos) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
//		return "entities.Proyectos[ id=" + id + " ]";
		return id + " " + nombre;
	}

	/**
	 * @return the facturasgeneradas
	 */
	public Facturasgeneradas getFacturasgeneradas() {
		return facturasgeneradas;
	}

	/**
	 * @param facturasgeneradas the facturasgeneradas to set
	 */
	public void setFacturasgeneradas(Facturasgeneradas facturasgeneradas) {
		this.facturasgeneradas = facturasgeneradas;
	}

	/**
	 * @return the porcAdmin
	 */
	public float getPorcAdmin() {
		return porcAdmin;
	}

	/**
	 * @param porcAdmin the porcAdmin to set
	 */
	public void setPorcAdmin(float porcAdmin) {
		this.porcAdmin = porcAdmin;
	}

	/**
	 * @return the porcComision
	 */
	public float getPorcComision() {
		return porcComision;
	}

	/**
	 * @param porcComision the porcComision to set
	 */
	public void setPorcComision(float porcComision) {
		this.porcComision = porcComision;
	}

	/**
	 * @return the porcUtilidad
	 */
	public float getPorcUtilidad() {
		return porcUtilidad;
	}

	/**
	 * @param porcUtilidad the porcUtilidad to set
	 */
	public void setPorcUtilidad(float porcUtilidad) {
		this.porcUtilidad = porcUtilidad;
	}

}
