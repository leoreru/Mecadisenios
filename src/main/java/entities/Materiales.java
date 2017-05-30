/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PC-HP
 */
@Entity
@Table(name = "materiales")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Materiales.findAll", query = "SELECT m FROM Materiales m")
	, @NamedQuery(name = "Materiales.findById", query = "SELECT m FROM Materiales m WHERE m.id = :id")
	, @NamedQuery(name = "Materiales.findByMedida1", query = "SELECT m FROM Materiales m WHERE m.medida1 = :medida1")
	, @NamedQuery(name = "Materiales.findByMedida2", query = "SELECT m FROM Materiales m WHERE m.medida2 = :medida2")
	, @NamedQuery(name = "Materiales.findByMedida3", query = "SELECT m FROM Materiales m WHERE m.medida3 = :medida3")
	, @NamedQuery(name = "Materiales.findByValor", query = "SELECT m FROM Materiales m WHERE m.valor = :valor")})
public class Materiales implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@NotNull
	@Column(name = "medida1")
	private float medida1;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Column(name = "medida2")
	private Float medida2;
	@Column(name = "medida3")
	private Float medida3;
	@Column(name = "valor")
	private BigDecimal valor;
/*
	Esto no me dejaba editar los registros en la página de administración de la tabla
	materiales proyecto(cuando se cambiaba el material, que hace parte de la clave
	principal, se duplicaba el registro, por que la instrucción de borrar no funcionaba).
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "materiales")
	private Collection<Materialesproyecto> materialesproyectoCollection;
*/
	@JoinColumn(name = "idMarcaMaterial", referencedColumnName = "id")
	@ManyToOne
	private Marcamaterial idMarcaMaterial;
	@JoinColumn(name = "idMaterialTipo", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Materialestipo idMaterialTipo;
	@JoinColumn(name = "idUnidadMed1", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Unidadesmedida idUnidadMed1;
	@JoinColumn(name = "idUnidadMed2", referencedColumnName = "id")
	@ManyToOne
	private Unidadesmedida idUnidadMed2;
	@JoinColumn(name = "idUnidadMed3", referencedColumnName = "id")
	@ManyToOne
	private Unidadesmedida idUnidadMed3;

	public Materiales() {
	}

	public Materiales(Integer id) {
		this.id = id;
	}

	public Materiales(Integer id, float medida1) {
		this.id = id;
		this.medida1 = medida1;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getMedida1() {
		return medida1;
	}

	public void setMedida1(float medida1) {
		this.medida1 = medida1;
	}

	public Float getMedida2() {
		return medida2;
	}

	public void setMedida2(Float medida2) {
		this.medida2 = medida2;
	}

	public Float getMedida3() {
		return medida3;
	}

	public void setMedida3(Float medida3) {
		this.medida3 = medida3;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
/*
	@XmlTransient
	public Collection<Materialesproyecto> getMaterialesproyectoCollection() {
		return materialesproyectoCollection;
	}

	public void setMaterialesproyectoCollection(Collection<Materialesproyecto> materialesproyectoCollection) {
		this.materialesproyectoCollection = materialesproyectoCollection;
	}
*/
	public Marcamaterial getIdMarcaMaterial() {
		return idMarcaMaterial;
	}

	public void setIdMarcaMaterial(Marcamaterial idMarcaMaterial) {
		this.idMarcaMaterial = idMarcaMaterial;
	}

	public Materialestipo getIdMaterialTipo() {
		return idMaterialTipo;
	}

	public void setIdMaterialTipo(Materialestipo idMaterialTipo) {
		this.idMaterialTipo = idMaterialTipo;
	}

	public Unidadesmedida getIdUnidadMed1() {
		return idUnidadMed1;
	}

	public void setIdUnidadMed1(Unidadesmedida idUnidadMed1) {
		this.idUnidadMed1 = idUnidadMed1;
	}

	public Unidadesmedida getIdUnidadMed2() {
		return idUnidadMed2;
	}

	public void setIdUnidadMed2(Unidadesmedida idUnidadMed2) {
		this.idUnidadMed2 = idUnidadMed2;
	}

	public Unidadesmedida getIdUnidadMed3() {
		return idUnidadMed3;
	}

	public void setIdUnidadMed3(Unidadesmedida idUnidadMed3) {
		this.idUnidadMed3 = idUnidadMed3;
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
		if (!(object instanceof Materiales)) {
			return false;
		}
		Materiales other = (Materiales) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		//return "entities.Materiales[ id=" + id + " ]";
		return idMaterialTipo.getNombre() + " - " + medida1 + " " + idUnidadMed1
				+ (idUnidadMed2 == null ? "":" X " + medida2 + " " + idUnidadMed2)
				+ (idUnidadMed3 == null ? "":" X " + medida3 + " " + idUnidadMed3)
				+ " - $" + valor;
	}

}
