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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
//import org.hibernate.annotations.LazyCollection;
//import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author PC-HP
 */
@Entity
@Table(name = "personanatural")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Personanatural.findAll", query = "SELECT p FROM Personanatural p")
	, @NamedQuery(name = "Personanatural.findById", query = "SELECT p FROM Personanatural p WHERE p.id = :id")
	, @NamedQuery(name = "Personanatural.findByNumDoc", query = "SELECT p FROM Personanatural p WHERE p.numDoc = :numDoc")
	, @NamedQuery(name = "Personanatural.findByNombres", query = "SELECT p FROM Personanatural p WHERE p.nombres = :nombres")
	, @NamedQuery(name = "Personanatural.findByApellido1", query = "SELECT p FROM Personanatural p WHERE p.apellido1 = :apellido1")
	, @NamedQuery(name = "Personanatural.findByApellido2", query = "SELECT p FROM Personanatural p WHERE p.apellido2 = :apellido2")
	, @NamedQuery(name = "Personanatural.findByCargo", query = "SELECT p FROM Personanatural p WHERE p.cargo = :cargo")
	, @NamedQuery(name = "Personanatural.findByEmail", query = "SELECT p FROM Personanatural p WHERE p.email = :email")
	, @NamedQuery(name = "Personanatural.findByTelefono", query = "SELECT p FROM Personanatural p WHERE p.telefono = :telefono")})
public class Personanatural implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
	private Integer id;
	@Basic(optional = false)
  @NotNull
  @Column(name = "numDoc")
	private long numDoc;
	@Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 40)
  @Column(name = "nombres")
	private String nombres;
	@Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "apellido1")
	private String apellido1;
	@Size(max = 30)
  @Column(name = "apellido2")
	private String apellido2;
	@Size(max = 30)
  @Column(name = "cargo")
	private String cargo;
	// Para que aceptara que el campo estuviera vacio le aumente a la expresión regular "^$|"
	// al principio.
	@Pattern(regexp="^$|[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
	@Size(max = 45)
  @Column(name = "email")
	private String email;
	@Size(max = 20)
  @Column(name = "telefono")
	private String telefono;
	@Size(max = 100)
  @Column(name = "direccion")
	private String direccion;
	@JoinColumns({
  	@JoinColumn(name = "codDep", referencedColumnName = "codDep")
  	, @JoinColumn(name = "codMun", referencedColumnName = "codMun")})
  @ManyToOne
	private Municipiosdane municipiosdane;
	//Mio, necesita la librería hibernate.
	//@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idClientePerNat")
	private Collection<Contactoscliente> contactosclienteCollection;
	@JoinColumn(name = "idTipoDoc", referencedColumnName = "id")
  @ManyToOne(optional = false)
	private Tipodocumento idTipoDoc;

	public Personanatural() {
	}

	public Personanatural(Integer id) {
		this.id = id;
	}

	public Personanatural(Integer id, long numDoc, String nombres, String apellido1) {
		this.id = id;
		this.numDoc = numDoc;
		this.nombres = nombres;
		this.apellido1 = apellido1;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public long getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(long numDoc) {
		this.numDoc = numDoc;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@XmlTransient
	public Collection<Contactoscliente> getContactosclienteCollection() {
		return contactosclienteCollection;
	}

	public void setContactosclienteCollection(Collection<Contactoscliente> contactosclienteCollection) {
		this.contactosclienteCollection = contactosclienteCollection;
	}

	public Tipodocumento getIdTipoDoc() {
		return idTipoDoc;
	}

	public void setIdTipoDoc(Tipodocumento idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
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
		if (!(object instanceof Personanatural)) {
			return false;
		}
		Personanatural other = (Personanatural) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		//return "entities.Personanatural[ id=" + id + " ]";
		return idTipoDoc.getTipodoc() + "-" + numDoc + " " + nombres + " " + apellido1 + " " +  (apellido2==null ? "":apellido2);
	}

	public String nombreCompleto() {
		return nombres + " " + apellido1 + " " +  (apellido2==null ? "":apellido2);		
	}
	
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the municipiosdane
	 */
	public Municipiosdane getMunicipiosdane() {
		return municipiosdane;
	}

	/**
	 * @param municipiosdane the municipiosdane to set
	 */
	public void setMunicipiosdane(Municipiosdane municipiosdane) {
		this.municipiosdane = municipiosdane;
	}
	
}
