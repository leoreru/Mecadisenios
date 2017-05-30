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
@Table(name = "otrosgastosproyecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Otrosgastosproyecto.findAll", query = "SELECT o FROM Otrosgastosproyecto o")
    , @NamedQuery(name = "Otrosgastosproyecto.findByIdProyecto", query = "SELECT o FROM Otrosgastosproyecto o WHERE o.otrosgastosproyectoPK.idProyecto = :idProyecto")
    , @NamedQuery(name = "Otrosgastosproyecto.findByIdTipoGasto", query = "SELECT o FROM Otrosgastosproyecto o WHERE o.otrosgastosproyectoPK.idTipoGasto = :idTipoGasto")
    , @NamedQuery(name = "Otrosgastosproyecto.findByCantidad", query = "SELECT o FROM Otrosgastosproyecto o WHERE o.cantidad = :cantidad")
    , @NamedQuery(name = "Otrosgastosproyecto.findByValUnitario", query = "SELECT o FROM Otrosgastosproyecto o WHERE o.valUnitario = :valUnitario")})
public class Otrosgastosproyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OtrosgastosproyectoPK otrosgastosproyectoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private float cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valUnitario")
    private BigDecimal valUnitario;
    @JoinColumn(name = "idProyecto", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyectos proyectos;
    @JoinColumn(name = "idTipoGasto", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tipogasto tipogasto;

    public Otrosgastosproyecto() {
    }

    public Otrosgastosproyecto(OtrosgastosproyectoPK otrosgastosproyectoPK) {
        this.otrosgastosproyectoPK = otrosgastosproyectoPK;
    }

    public Otrosgastosproyecto(OtrosgastosproyectoPK otrosgastosproyectoPK, float cantidad, BigDecimal valUnitario) {
        this.otrosgastosproyectoPK = otrosgastosproyectoPK;
        this.cantidad = cantidad;
        this.valUnitario = valUnitario;
    }

    public Otrosgastosproyecto(int idProyecto, short idTipoGasto) {
        this.otrosgastosproyectoPK = new OtrosgastosproyectoPK(idProyecto, idTipoGasto);
    }

    public OtrosgastosproyectoPK getOtrosgastosproyectoPK() {
        return otrosgastosproyectoPK;
    }

    public void setOtrosgastosproyectoPK(OtrosgastosproyectoPK otrosgastosproyectoPK) {
        this.otrosgastosproyectoPK = otrosgastosproyectoPK;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getValUnitario() {
        return valUnitario;
    }

    public void setValUnitario(BigDecimal valUnitario) {
        this.valUnitario = valUnitario;
    }

    public Proyectos getProyectos() {
        return proyectos;
    }

    public void setProyectos(Proyectos proyectos) {
        this.proyectos = proyectos;
    }

    public Tipogasto getTipogasto() {
        return tipogasto;
    }

    public void setTipogasto(Tipogasto tipogasto) {
        this.tipogasto = tipogasto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otrosgastosproyectoPK != null ? otrosgastosproyectoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Otrosgastosproyecto)) {
            return false;
        }
        Otrosgastosproyecto other = (Otrosgastosproyecto) object;
        if ((this.otrosgastosproyectoPK == null && other.otrosgastosproyectoPK != null) || (this.otrosgastosproyectoPK != null && !this.otrosgastosproyectoPK.equals(other.otrosgastosproyectoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "entities.Otrosgastosproyecto[ otrosgastosproyectoPK=" + otrosgastosproyectoPK + " ]";
				return tipogasto.getNombre() + " proy.: " + proyectos.getNombre();
    }
    
}
