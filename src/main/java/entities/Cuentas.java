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
@Table(name = "cuentas")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Cuentas.findAll", query = "SELECT c FROM Cuentas c")
	, @NamedQuery(name = "Cuentas.findById", query = "SELECT c FROM Cuentas c WHERE c.id = :id")
	, @NamedQuery(name = "Cuentas.findByNoCuenta", query = "SELECT c FROM Cuentas c WHERE c.noCuenta = :noCuenta")
	, @NamedQuery(name = "Cuentas.findByDescripcion", query = "SELECT c FROM Cuentas c WHERE c.descripcion = :descripcion")})
public class Cuentas implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Short id;
	@Basic(optional = false)
	@NotNull
	@Column(name = "noCuenta")
	private long noCuenta;
	@Size(max = 255)
	@Column(name = "descripcion")
	private String descripcion;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaDestino")
	private Collection<Giros> girosCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaOrigen")
	private Collection<Giros> girosCollection1;
	@JoinColumn(name = "idBanco", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Bancos idBanco;
	@JoinColumn(name = "idTitularPerJur", referencedColumnName = "id")
	@ManyToOne
	private Personajuridica idTitularPerJur;
	@JoinColumn(name = "idTitularPerNat", referencedColumnName = "id")
	@ManyToOne
	private Personanatural idTitularPerNat;
	@JoinColumn(name = "idTipo", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Tipocuenta idTipo;

	public Cuentas() {
	}

	public Cuentas(Short id) {
		this.id = id;
	}

	public Cuentas(Short id, long noCuenta) {
		this.id = id;
		this.noCuenta = noCuenta;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public long getNoCuenta() {
		return noCuenta;
	}

	public void setNoCuenta(long noCuenta) {
		this.noCuenta = noCuenta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@XmlTransient
	public Collection<Giros> getGirosCollection() {
		return girosCollection;
	}

	public void setGirosCollection(Collection<Giros> girosCollection) {
		this.girosCollection = girosCollection;
	}

	@XmlTransient
	public Collection<Giros> getGirosCollection1() {
		return girosCollection1;
	}

	public void setGirosCollection1(Collection<Giros> girosCollection1) {
		this.girosCollection1 = girosCollection1;
	}

	public Bancos getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(Bancos idBanco) {
		this.idBanco = idBanco;
	}

	public Personajuridica getIdTitularPerJur() {
		return idTitularPerJur;
	}

	public void setIdTitularPerJur(Personajuridica idTitularPerJur) {
		this.idTitularPerJur = idTitularPerJur;
	}

	public Personanatural getIdTitularPerNat() {
		return idTitularPerNat;
	}

	public void setIdTitularPerNat(Personanatural idTitularPerNat) {
		this.idTitularPerNat = idTitularPerNat;
	}

	public Tipocuenta getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Tipocuenta idTipo) {
		this.idTipo = idTipo;
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
		if (!(object instanceof Cuentas)) {
			return false;
		}
		Cuentas other = (Cuentas) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		//return "entities.Cuentas[ id=" + id + " ]";
		return noCuenta + " " 
			+ (idTitularPerNat == null ? idTitularPerJur.getNombre():idTitularPerNat.getNombres() + " " +idTitularPerNat.getApellido1())
			+ " " + idBanco.getNombre();
	}

}
