/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC-HP
 */
@Entity
@Table(name = "facturasgeneradas")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Facturasgeneradas.findAll", query = "SELECT f FROM Facturasgeneradas f")
	, @NamedQuery(name = "Facturasgeneradas.findByIdProyecto", query = "SELECT f FROM Facturasgeneradas f WHERE f.idProyecto = :idProyecto")
	, @NamedQuery(name = "Facturasgeneradas.findByFecha", query = "SELECT f FROM Facturasgeneradas f WHERE f.fecha = :fecha")
	, @NamedQuery(name = "Facturasgeneradas.findByPorcIva", query = "SELECT f FROM Facturasgeneradas f WHERE f.porcIva = :porcIva")
	, @NamedQuery(name = "Facturasgeneradas.findByValorSinIva", query = "SELECT f FROM Facturasgeneradas f WHERE f.valorSinIva = :valorSinIva")})
public class Facturasgeneradas implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "idProyecto")
	private Integer idProyecto;
	@Basic(optional = false)
  @NotNull
  @Column(name = "fecha")
  @Temporal(TemporalType.DATE)
	private Date fecha;
	@Basic(optional = false)
  @NotNull
  @Column(name = "porcIva")
	private float porcIva;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Basic(optional = false)
  @NotNull
  @Column(name = "valorSinIva")
	private BigDecimal valorSinIva;
	@JoinColumn(name = "idProyecto", referencedColumnName = "id", insertable = false, updatable = false)
  @OneToOne(optional = false)
	private Proyectos proyectos;

	public Facturasgeneradas() {
	}

	public Facturasgeneradas(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public Facturasgeneradas(Integer idProyecto, Date fecha, float porcIva, BigDecimal valorSinIva) {
		this.idProyecto = idProyecto;
		this.fecha = fecha;
		this.porcIva = porcIva;
		this.valorSinIva = valorSinIva;
	}

	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public float getPorcIva() {
		return porcIva;
	}

	public void setPorcIva(float porcIva) {
		this.porcIva = porcIva;
	}

	public BigDecimal getValorSinIva() {
		return valorSinIva;
	}

	public void setValorSinIva(BigDecimal valorSinIva) {
		this.valorSinIva = valorSinIva;
	}

	public Proyectos getProyectos() {
		return proyectos;
	}

	public void setProyectos(Proyectos proyectos) {
		this.proyectos = proyectos;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idProyecto != null ? idProyecto.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Facturasgeneradas)) {
			return false;
		}
		Facturasgeneradas other = (Facturasgeneradas) object;
		if ((this.idProyecto == null && other.idProyecto != null) || (this.idProyecto != null && !this.idProyecto.equals(other.idProyecto))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entities.Facturasgeneradas[ idProyecto=" + idProyecto + " ]";
	}
	
}
