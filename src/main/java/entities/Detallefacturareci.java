/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC-HP
 */
@Entity
@Table(name = "detallefacturareci")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Detallefacturareci.findAll", query = "SELECT d FROM Detallefacturareci d")
	, @NamedQuery(name = "Detallefacturareci.findByIdFacturaReci", query = "SELECT d FROM Detallefacturareci d WHERE d.detallefacturareciPK.idFacturaReci = :idFacturaReci")
	, @NamedQuery(name = "Detallefacturareci.findByIdMaterial", query = "SELECT d FROM Detallefacturareci d WHERE d.detallefacturareciPK.idMaterial = :idMaterial")
	, @NamedQuery(name = "Detallefacturareci.findByCantidad", query = "SELECT d FROM Detallefacturareci d WHERE d.cantidad = :cantidad")
	, @NamedQuery(name = "Detallefacturareci.findByValorSinIva", query = "SELECT d FROM Detallefacturareci d WHERE d.valorSinIva = :valorSinIva")
	, @NamedQuery(name = "Detallefacturareci.findByPorcIva", query = "SELECT d FROM Detallefacturareci d WHERE d.porcIva = :porcIva")})
public class Detallefacturareci implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected DetallefacturareciPK detallefacturareciPK;
	@Basic(optional = false)
  @NotNull
  @Column(name = "cantidad")
	private float cantidad;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Basic(optional = false)
  @NotNull
  @Column(name = "valorSinIva")
	private BigDecimal valorSinIva;
	@Basic(optional = false)
  @NotNull
  @Column(name = "porcIva")
	private float porcIva;
	@JoinColumn(name = "idFacturaReci", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne(optional = false)
	private Facturasrecibidas facturasrecibidas;
	@JoinColumn(name = "idMaterial", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne(optional = false)
	private Materiales materiales;

	public Detallefacturareci() {
	}

	public Detallefacturareci(DetallefacturareciPK detallefacturareciPK) {
		this.detallefacturareciPK = detallefacturareciPK;
	}

	public Detallefacturareci(DetallefacturareciPK detallefacturareciPK, float cantidad, BigDecimal valorSinIva, float porcIva) {
		this.detallefacturareciPK = detallefacturareciPK;
		this.cantidad = cantidad;
		this.valorSinIva = valorSinIva;
		this.porcIva = porcIva;
	}

	public Detallefacturareci(int idFacturaReci, int idMaterial) {
		this.detallefacturareciPK = new DetallefacturareciPK(idFacturaReci, idMaterial);
	}

	public DetallefacturareciPK getDetallefacturareciPK() {
		return detallefacturareciPK;
	}

	public void setDetallefacturareciPK(DetallefacturareciPK detallefacturareciPK) {
		this.detallefacturareciPK = detallefacturareciPK;
	}

	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getValorSinIva() {
		return valorSinIva;
	}

	public void setValorSinIva(BigDecimal valorSinIva) {
		this.valorSinIva = valorSinIva;
	}

	public float getPorcIva() {
		return porcIva;
	}

	public void setPorcIva(float porcIva) {
		this.porcIva = porcIva;
	}

	public Facturasrecibidas getFacturasrecibidas() {
		return facturasrecibidas;
	}

	public void setFacturasrecibidas(Facturasrecibidas facturasrecibidas) {
		this.facturasrecibidas = facturasrecibidas;
	}

	public Materiales getMateriales() {
		return materiales;
	}

	public void setMateriales(Materiales materiales) {
		this.materiales = materiales;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (detallefacturareciPK != null ? detallefacturareciPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Detallefacturareci)) {
			return false;
		}
		Detallefacturareci other = (Detallefacturareci) object;
		if ((this.detallefacturareciPK == null && other.detallefacturareciPK != null) || (this.detallefacturareciPK != null && !this.detallefacturareciPK.equals(other.detallefacturareciPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entities.Detallefacturareci[ detallefacturareciPK=" + detallefacturareciPK + " ]";
	}
	
}
