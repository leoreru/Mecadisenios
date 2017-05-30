/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC-HP
 */
@Entity
@Table(name = "facturasrecibidas")
@XmlRootElement
@NamedQueries({
	 @NamedQuery(name = "Facturasrecibidas.findAll", query = "SELECT f FROM Facturasrecibidas f")
	,@NamedQuery(name = "Facturasrecibidas.findById", query = "SELECT f FROM Facturasrecibidas f WHERE f.id = :id")
	,@NamedQuery(name = "Facturasrecibidas.findByNumFac", query = "SELECT f FROM Facturasrecibidas f WHERE f.numFac = :numFac")
	,@NamedQuery(name = "Facturasrecibidas.findByFechaExp", query = "SELECT f FROM Facturasrecibidas f WHERE f.fechaExp = :fechaExp")
	,@NamedQuery(name = "Facturasrecibidas.findByPorcDescuento", query = "SELECT f FROM Facturasrecibidas f WHERE f.porcDescuento = :porcDescuento")})
public class Facturasrecibidas implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Column(name = "numFac")
	private BigInteger numFac;
	@Column(name = "fechaExp")
	@Temporal(TemporalType.DATE)
	private Date fechaExp;
	@JoinColumn(name = "idProveedorPerJur", referencedColumnName = "id")
	@ManyToOne
	private Personajuridica idProveedorPerJur;
	@JoinColumn(name = "idProveedorPerNat", referencedColumnName = "id")
	@ManyToOne
	private Personanatural idProveedorPerNat;
	@JoinColumn(name = "idProyecto", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Proyectos idProyecto;
  @Column(name = "porcDescuento")
	private Float porcDescuento;
	
	public Facturasrecibidas() {
	}

	public Facturasrecibidas(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigInteger getNumFac() {
		return numFac;
	}

	public void setNumFac(BigInteger numFac) {
		this.numFac = numFac;
	}

	public Date getFechaExp() {
		return fechaExp;
	}

	public void setFechaExp(Date fechaExp) {
		this.fechaExp = fechaExp;
	}

	public Personajuridica getIdProveedorPerJur() {
		return idProveedorPerJur;
	}

	public void setIdProveedorPerJur(Personajuridica idProveedorPerJur) {
		this.idProveedorPerJur = idProveedorPerJur;
	}

	public Personanatural getIdProveedorPerNat() {
		return idProveedorPerNat;
	}

	public void setIdProveedorPerNat(Personanatural idProveedorPerNat) {
		this.idProveedorPerNat = idProveedorPerNat;
	}

	public Proyectos getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Proyectos idProyecto) {
		this.idProyecto = idProyecto;
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
		if (!(object instanceof Facturasrecibidas)) {
			return false;
		}
		Facturasrecibidas other = (Facturasrecibidas) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		//return "entities.Facturasrecibidas[ id=" + id + " ]";
		return numFac + " "
				+ (idProveedorPerNat == null ? idProveedorPerJur.getNombre():idProveedorPerNat.getNombres() + " " +idProveedorPerNat.getApellido1())
				+	" - proy: " + idProyecto.toString();
	}

	/**
	 * @return the porcDescuento
	 */
	public Float getPorcDescuento() {
		return porcDescuento;
	}

	/**
	 * @param porcDescuento the porcDescuento to set
	 */
	public void setPorcDescuento(Float porcDescuento) {
		this.porcDescuento = porcDescuento;
	}

}
