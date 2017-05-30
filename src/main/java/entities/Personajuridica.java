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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
//import org.hibernate.annotations.LazyCollection;
//import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author PC-HP
 */
@Entity
@Table(name = "personajuridica")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Personajuridica.findAll", query = "SELECT p FROM Personajuridica p")
	, @NamedQuery(name = "Personajuridica.findById", query = "SELECT p FROM Personajuridica p WHERE p.id = :id")
	, @NamedQuery(name = "Personajuridica.findByNombre", query = "SELECT p FROM Personajuridica p WHERE p.nombre = :nombre")
	, @NamedQuery(name = "Personajuridica.findByNit", query = "SELECT p FROM Personajuridica p WHERE p.nit = :nit")
	, @NamedQuery(name = "Personajuridica.findByDireccion", query = "SELECT p FROM Personajuridica p WHERE p.direccion = :direccion")
	, @NamedQuery(name = "Personajuridica.findByTelefono", query = "SELECT p FROM Personajuridica p WHERE p.telefono = :telefono")})
public class Personajuridica implements Serializable {

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
	@Basic(optional = false)
  @NotNull
  @Column(name = "nit")
	private long nit;
	@Size(max = 100)
  @Column(name = "direccion")
	private String direccion;
	@Size(max = 20)
  @Column(name = "telefono")
	private String telefono;
	@JoinColumns({
  	@JoinColumn(name = "codDep", referencedColumnName = "codDep")
  	, @JoinColumn(name = "codMun", referencedColumnName = "codMun")})
  @ManyToOne
	private Municipiosdane municipiosdane;
	//Mio, necesita la librería hibernate.
	//@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idClientePerJur")
	private Collection<Contactoscliente> contactosclienteCollection;

	public Personajuridica() {
	}

	public Personajuridica(Integer id) {
		this.id = id;
	}

	public Personajuridica(Integer id, String nombre, long nit) {
		this.id = id;
		this.nombre = nombre;
		this.nit = nit;
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

	public long getNit() {
		return nit;
	}

	public void setNit(long nit) {
		this.nit = nit;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Municipiosdane getMunicipiosdane() {
		return municipiosdane;
	}

	public void setMunicipiosdane(Municipiosdane municipiosdane) {
		this.municipiosdane = municipiosdane;
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
		if (!(object instanceof Personajuridica)) {
			return false;
		}
		Personajuridica other = (Personajuridica) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		//return "entities.Personajuridica[ id=" + id + " ]";
		return "Nit " + nit + " " + nombre;
	}

	/**
	 * @return the contactosclienteCollection
	 */
	public Collection<Contactoscliente> getContactosclienteCollection() {
		return contactosclienteCollection;
	}

	/**
	 * @param contactosclienteCollection the contactosclienteCollection to set
	 */
	public void setContactosclienteCollection(Collection<Contactoscliente> contactosclienteCollection) {
		this.contactosclienteCollection = contactosclienteCollection;
	}
	
}
