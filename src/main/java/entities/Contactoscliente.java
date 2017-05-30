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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC-HP
 */
@Entity
@Table(name = "contactoscliente")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Contactoscliente.findAll", query = "SELECT c FROM Contactoscliente c")
	, @NamedQuery(name = "Contactoscliente.findById", query = "SELECT c FROM Contactoscliente c WHERE c.id = :id")})
public class Contactoscliente implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
	private Integer id;
	@JoinColumn(name = "idClientePerJur", referencedColumnName = "id")
  @ManyToOne
	private Personajuridica idClientePerJur;
	@JoinColumn(name = "idClientePerNat", referencedColumnName = "id")
  @ManyToOne
	private Personanatural idClientePerNat;
	@JoinColumn(name = "idContactoPerNat", referencedColumnName = "id")
  @ManyToOne(optional = false)
	private Personanatural idContactoPerNat;

	public Contactoscliente() {
	}

	public Contactoscliente(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Personajuridica getIdClientePerJur() {
		return idClientePerJur;
	}

	public void setIdClientePerJur(Personajuridica idClientePerJur) {
		this.idClientePerJur = idClientePerJur;
	}

	public Personanatural getIdClientePerNat() {
		return idClientePerNat;
	}

	public void setIdClientePerNat(Personanatural idClientePerNat) {
		this.idClientePerNat = idClientePerNat;
	}

	public Personanatural getIdContactoPerNat() {
		return idContactoPerNat;
	}

	public void setIdContactoPerNat(Personanatural idContactoPerNat) {
		this.idContactoPerNat = idContactoPerNat;
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
		if (!(object instanceof Contactoscliente)) {
			return false;
		}
		Contactoscliente other = (Contactoscliente) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		//return "entities.Contactoscliente[ id=" + id + " ]";
		return "'"+(idClientePerNat == null ? idClientePerJur.getNombre():idClientePerNat.getNombres())+"'"
						+ " contacto: " + "'"+idContactoPerNat.getNombres()+"'";
	}
	
}
