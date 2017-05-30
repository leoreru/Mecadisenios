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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "giros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Giros.findAll", query = "SELECT g FROM Giros g")
    , @NamedQuery(name = "Giros.findById", query = "SELECT g FROM Giros g WHERE g.id = :id")
    , @NamedQuery(name = "Giros.findByValor", query = "SELECT g FROM Giros g WHERE g.valor = :valor")
    , @NamedQuery(name = "Giros.findByRetenciones", query = "SELECT g FROM Giros g WHERE g.retenciones = :retenciones")
    , @NamedQuery(name = "Giros.findByDescripcion", query = "SELECT g FROM Giros g WHERE g.descripcion = :descripcion")})
public class Giros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private BigDecimal valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "porcRetenciones")
    private float retenciones;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "idCuentaDestino", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cuentas idCuentaDestino;
    @JoinColumn(name = "idCuentaOrigen", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cuentas idCuentaOrigen;
    @JoinColumn(name = "idProyecto", referencedColumnName = "id")
    @ManyToOne
    private Proyectos idProyecto;

    public Giros() {
    }

    public Giros(Integer id) {
        this.id = id;
    }

    public Giros(Integer id, BigDecimal valor, float retenciones) {
        this.id = id;
        this.valor = valor;
        this.retenciones = retenciones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public float getRetenciones() {
        return retenciones;
    }

    public void setRetenciones(float retenciones) {
        this.retenciones = retenciones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cuentas getIdCuentaDestino() {
        return idCuentaDestino;
    }

    public void setIdCuentaDestino(Cuentas idCuentaDestino) {
        this.idCuentaDestino = idCuentaDestino;
    }

    public Cuentas getIdCuentaOrigen() {
        return idCuentaOrigen;
    }

    public void setIdCuentaOrigen(Cuentas idCuentaOrigen) {
        this.idCuentaOrigen = idCuentaOrigen;
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
        if (!(object instanceof Giros)) {
            return false;
        }
        Giros other = (Giros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "entities.Giros[ id=" + id + " ]";
				return idCuentaOrigen.getNoCuenta()
						+ " -> " + idCuentaDestino.getNoCuenta()
						+ " Proy: " + idProyecto.getNombre();
    }
    
}
