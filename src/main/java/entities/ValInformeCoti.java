/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC-HP
 */
// No se usa.
@Entity
//@Table(name = "SP_GenerarFactura")
@XmlRootElement
public class ValInformeCoti implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "valComision")
	private double valComision;
	@Basic(optional = false)
	@Column(name = "valUtilidad")
	private double valUtilidad;
	@Basic(optional = false)
	@Column(name = "valAdmin")
	private double valAdmin;

	public ValInformeCoti() {
	}

	/**
	 * @return the valComision
	 */
	public double getValComision() {
		return valComision;
	}

	/**
	 * @param valComision the valComision to set
	 */
	public void setValComision(double valComision) {
		this.valComision = valComision;
	}

	/**
	 * @return the valUtilidad
	 */
	public double getValUtilidad() {
		return valUtilidad;
	}

	/**
	 * @param valUtilidad the valUtilidad to set
	 */
	public void setValUtilidad(double valUtilidad) {
		this.valUtilidad = valUtilidad;
	}

	/**
	 * @return the valAdmin
	 */
	public double getValAdmin() {
		return valAdmin;
	}

	/**
	 * @param valAdmin the valAdmin to set
	 */
	public void setValAdmin(double valAdmin) {
		this.valAdmin = valAdmin;
	}

}
