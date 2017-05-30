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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC-HP
 */
@Entity
@Table(name = "vw_costoestandardvscostoreal")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "VwCostoestandardvscostoreal.findAll", query = "SELECT v FROM VwCostoestandardvscostoreal v")
	, @NamedQuery(name = "VwCostoestandardvscostoreal.findByIdProy", query = "SELECT v FROM VwCostoestandardvscostoreal v WHERE v.idProy = :idProy")
	, @NamedQuery(name = "VwCostoestandardvscostoreal.findByProyecto", query = "SELECT v FROM VwCostoestandardvscostoreal v WHERE v.proyecto = :proyecto")
	, @NamedQuery(name = "VwCostoestandardvscostoreal.findByValorCotizadoMat", query = "SELECT v FROM VwCostoestandardvscostoreal v WHERE v.valorCotizadoMat = :valorCotizadoMat")
	, @NamedQuery(name = "VwCostoestandardvscostoreal.findByValorRealMat", query = "SELECT v FROM VwCostoestandardvscostoreal v WHERE v.valorRealMat = :valorRealMat")})
public class VwCostoestandardvscostoreal implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
  @NotNull
  @Column(name = "idProy")
	private int idProy;
	@Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 45)
  @Column(name = "Proyecto")
	private String proyecto;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Column(name = "ValorCotizadoMat")
	private Double valorCotizadoMat;
	@Column(name = "ValorRealMat")
	private Double valorRealMat;

	public VwCostoestandardvscostoreal() {
	}

	public int getIdProy() {
		return idProy;
	}

	public void setIdProy(int idProy) {
		this.idProy = idProy;
	}

	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}

	public Double getValorCotizadoMat() {
		return valorCotizadoMat;
	}

	public void setValorCotizadoMat(Double valorCotizadoMat) {
		this.valorCotizadoMat = valorCotizadoMat;
	}

	public Double getValorRealMat() {
		return valorRealMat;
	}

	public void setValorRealMat(Double valorRealMat) {
		this.valorRealMat = valorRealMat;
	}
	
}
