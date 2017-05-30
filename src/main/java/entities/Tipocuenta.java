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
@Table(name = "tipocuenta")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Tipocuenta.findAll", query = "SELECT t FROM Tipocuenta t")
	, @NamedQuery(name = "Tipocuenta.findById", query = "SELECT t FROM Tipocuenta t WHERE t.id = :id")
	, @NamedQuery(name = "Tipocuenta.findByNombre", query = "SELECT t FROM Tipocuenta t WHERE t.nombre = :nombre")})
public class Tipocuenta implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@NotNull
	@Basic(optional = false)
	@Column(name = "id")
	private Short id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "nombre")
	private String nombre;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipo")
	private Collection<Cuentas> cuentasCollection;

	public Tipocuenta() {
	}

	public Tipocuenta(Short id) {
		this.id = id;
	}

	public Tipocuenta(Short id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlTransient
	public Collection<Cuentas> getCuentasCollection() {
		return cuentasCollection;
	}

	public void setCuentasCollection(Collection<Cuentas> cuentasCollection) {
		this.cuentasCollection = cuentasCollection;
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
		if (!(object instanceof Tipocuenta)) {
			return false;
		}
		Tipocuenta other = (Tipocuenta) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		//return "entities.Tipocuenta[ id=" + id + " ]";
		return nombre;
	}

}
