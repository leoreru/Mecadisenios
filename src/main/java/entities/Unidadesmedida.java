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
@Table(name = "unidadesmedida")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Unidadesmedida.findAll", query = "SELECT u FROM Unidadesmedida u")
	, @NamedQuery(name = "Unidadesmedida.findById", query = "SELECT u FROM Unidadesmedida u WHERE u.id = :id")
	, @NamedQuery(name = "Unidadesmedida.findByNombre", query = "SELECT u FROM Unidadesmedida u WHERE u.nombre = :nombre")})
public class Unidadesmedida implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Short id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 30)
	@Column(name = "nombre")
	private String nombre;
	@JoinColumn(name = "idTipoUnd", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Tipounidad idTipoUnd;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idUnidadMed1")
	private Collection<Materiales> materialesCollection;
	@OneToMany(mappedBy = "idUnidadMed2")
	private Collection<Materiales> materialesCollection1;
	@OneToMany(mappedBy = "idUnidadMed3")
	private Collection<Materiales> materialesCollection2;

	public Unidadesmedida() {
	}

	public Unidadesmedida(Short id) {
		this.id = id;
	}

	public Unidadesmedida(Short id, String nombre) {
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

	public Tipounidad getIdTipoUnd() {
		return idTipoUnd;
	}

	public void setIdTipoUnd(Tipounidad idTipoUnd) {
		this.idTipoUnd = idTipoUnd;
	}

	@XmlTransient
	public Collection<Materiales> getMaterialesCollection() {
		return materialesCollection;
	}

	public void setMaterialesCollection(Collection<Materiales> materialesCollection) {
		this.materialesCollection = materialesCollection;
	}

	@XmlTransient
	public Collection<Materiales> getMaterialesCollection1() {
		return materialesCollection1;
	}

	public void setMaterialesCollection1(Collection<Materiales> materialesCollection1) {
		this.materialesCollection1 = materialesCollection1;
	}

	@XmlTransient
	public Collection<Materiales> getMaterialesCollection2() {
		return materialesCollection2;
	}

	public void setMaterialesCollection2(Collection<Materiales> materialesCollection2) {
		this.materialesCollection2 = materialesCollection2;
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
		if (!(object instanceof Unidadesmedida)) {
			return false;
		}
		Unidadesmedida other = (Unidadesmedida) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		//return "entities.Unidadesmedida[ id=" + id + " ]";
		return nombre;
	}

}
