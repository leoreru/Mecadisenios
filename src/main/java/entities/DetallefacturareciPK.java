/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author PC-HP
 */
@Embeddable
public class DetallefacturareciPK implements Serializable {

	@Basic(optional = false)
  @NotNull
  @Column(name = "idFacturaReci")
	private int idFacturaReci;
	@Basic(optional = false)
  @NotNull
  @Column(name = "idMaterial")
	private int idMaterial;

	public DetallefacturareciPK() {
	}

	public DetallefacturareciPK(int idFacturaReci, int idMaterial) {
		this.idFacturaReci = idFacturaReci;
		this.idMaterial = idMaterial;
	}

	public int getIdFacturaReci() {
		return idFacturaReci;
	}

	public void setIdFacturaReci(int idFacturaReci) {
		this.idFacturaReci = idFacturaReci;
	}

	public int getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(int idMaterial) {
		this.idMaterial = idMaterial;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (int) idFacturaReci;
		hash += (int) idMaterial;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof DetallefacturareciPK)) {
			return false;
		}
		DetallefacturareciPK other = (DetallefacturareciPK) object;
		if (this.idFacturaReci != other.idFacturaReci) {
			return false;
		}
		if (this.idMaterial != other.idMaterial) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entities.DetallefacturareciPK[ idFacturaReci=" + idFacturaReci + ", idMaterial=" + idMaterial + " ]";
	}
	
}
