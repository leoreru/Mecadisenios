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
@Table(name = "materialescategoria")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Materialescategoria.findAll", query = "SELECT m FROM Materialescategoria m")
	, @NamedQuery(name = "Materialescategoria.findById", query = "SELECT m FROM Materialescategoria m WHERE m.id = :id")
	, @NamedQuery(name = "Materialescategoria.findByNombre", query = "SELECT m FROM Materialescategoria m WHERE m.nombre = :nombre")})
public class Materialescategoria implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Short id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "nombre")
	private String nombre;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idCategoria")
	private Collection<Materialestipo> materialestipoCollection;

	public Materialescategoria() {
	}

	public Materialescategoria(Short id) {
		this.id = id;
	}

	public Materialescategoria(Short id, String nombre) {
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
	public Collection<Materialestipo> getMaterialestipoCollection() {
		return materialestipoCollection;
	}

	public void setMaterialestipoCollection(Collection<Materialestipo> materialestipoCollection) {
		this.materialestipoCollection = materialestipoCollection;
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
		if (!(object instanceof Materialescategoria)) {
			return false;
		}
		Materialescategoria other = (Materialescategoria) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		//return "entities.Materialescategoria[ id=" + id + " ]";
		return nombre;
	}

}
