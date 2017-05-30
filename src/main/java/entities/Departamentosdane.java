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
@Table(name = "departamentosdane")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Departamentosdane.findAll", query = "SELECT d FROM Departamentosdane d")
	, @NamedQuery(name = "Departamentosdane.findByCodDep", query = "SELECT d FROM Departamentosdane d WHERE d.codDep = :codDep")
	, @NamedQuery(name = "Departamentosdane.findByNombre", query = "SELECT d FROM Departamentosdane d WHERE d.nombre = :nombre")})
public class Departamentosdane implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "codDep")
	private Short codDep;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 20)
	@Column(name = "nombre")
	private String nombre;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "departamentosdane")
	private Collection<Municipiosdane> municipiosdaneCollection;

	public Departamentosdane() {
	}

	public Departamentosdane(Short codDep) {
		this.codDep = codDep;
	}

	public Departamentosdane(Short codDep, String nombre) {
		this.codDep = codDep;
		this.nombre = nombre;
	}

	public Short getCodDep() {
		return codDep;
	}

	public void setCodDep(Short codDep) {
		this.codDep = codDep;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlTransient
	public Collection<Municipiosdane> getMunicipiosdaneCollection() {
		return municipiosdaneCollection;
	}

	public void setMunicipiosdaneCollection(Collection<Municipiosdane> municipiosdaneCollection) {
		this.municipiosdaneCollection = municipiosdaneCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (codDep != null ? codDep.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Departamentosdane)) {
			return false;
		}
		Departamentosdane other = (Departamentosdane) object;
		if ((this.codDep == null && other.codDep != null) || (this.codDep != null && !this.codDep.equals(other.codDep))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		//return "entities.Departamentosdane[ codDep=" + codDep + " ]";
		return nombre;
	}

}
