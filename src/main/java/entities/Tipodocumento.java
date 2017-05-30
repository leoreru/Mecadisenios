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
@Table(name = "tipodocumento")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Tipodocumento.findAll", query = "SELECT t FROM Tipodocumento t")
	, @NamedQuery(name = "Tipodocumento.findById", query = "SELECT t FROM Tipodocumento t WHERE t.id = :id")
	, @NamedQuery(name = "Tipodocumento.findByTipodoc", query = "SELECT t FROM Tipodocumento t WHERE t.tipodoc = :tipodoc")
	, @NamedQuery(name = "Tipodocumento.findByNombre", query = "SELECT t FROM Tipodocumento t WHERE t.nombre = :nombre")})
public class Tipodocumento implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "id")
	private Short id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 3)
	@Column(name = "tipodoc")
	private String tipodoc;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "nombre")
	private String nombre;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoDoc")
	private Collection<Personanatural> personanaturalCollection;

	public Tipodocumento() {
	}

	public Tipodocumento(Short id) {
		this.id = id;
	}

	public Tipodocumento(Short id, String tipodoc, String nombre) {
		this.id = id;
		this.tipodoc = tipodoc;
		this.nombre = nombre;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getTipodoc() {
		return tipodoc;
	}

	public void setTipodoc(String tipodoc) {
		this.tipodoc = tipodoc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlTransient
	public Collection<Personanatural> getPersonanaturalCollection() {
		return personanaturalCollection;
	}

	public void setPersonanaturalCollection(Collection<Personanatural> personanaturalCollection) {
		this.personanaturalCollection = personanaturalCollection;
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
		if (!(object instanceof Tipodocumento)) {
			return false;
		}
		Tipodocumento other = (Tipodocumento) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		//return "entities.Tipodocumento[ id=" + id + " ]";
		return nombre;
	}

}
