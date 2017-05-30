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
@Table(name = "materialesproyecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materialesproyecto.findAll", query = "SELECT m FROM Materialesproyecto m")
    , @NamedQuery(name = "Materialesproyecto.findByIdProyecto", query = "SELECT m FROM Materialesproyecto m WHERE m.materialesproyectoPK.idProyecto = :idProyecto")
    , @NamedQuery(name = "Materialesproyecto.findByIdMaterial", query = "SELECT m FROM Materialesproyecto m WHERE m.materialesproyectoPK.idMaterial = :idMaterial")
    , @NamedQuery(name = "Materialesproyecto.findByCantidad", query = "SELECT m FROM Materialesproyecto m WHERE m.cantidad = :cantidad")
    , @NamedQuery(name = "Materialesproyecto.findByValorCotizado", query = "SELECT m FROM Materialesproyecto m WHERE m.valorCotizado = :valorCotizado")})
public class Materialesproyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MaterialesproyectoPK materialesproyectoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private float cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valorCotizado")
    private BigDecimal valorCotizado;
    @JoinColumn(name = "idMaterial", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materiales materiales;
    @JoinColumn(name = "idProyecto", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyectos proyectos;

    public Materialesproyecto() {
    }

    public Materialesproyecto(MaterialesproyectoPK materialesproyectoPK) {
        this.materialesproyectoPK = materialesproyectoPK;
    }

    public Materialesproyecto(MaterialesproyectoPK materialesproyectoPK, float cantidad, BigDecimal valorCotizado) {
        this.materialesproyectoPK = materialesproyectoPK;
        this.cantidad = cantidad;
        this.valorCotizado = valorCotizado;
    }

    public Materialesproyecto(int idProyecto, int idMaterial) {
        this.materialesproyectoPK = new MaterialesproyectoPK(idProyecto, idMaterial);
    }

    public MaterialesproyectoPK getMaterialesproyectoPK() {
        return materialesproyectoPK;
    }

    public void setMaterialesproyectoPK(MaterialesproyectoPK materialesproyectoPK) {
        this.materialesproyectoPK = materialesproyectoPK;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getValorCotizado() {
        return valorCotizado;
    }

    public void setValorCotizado(BigDecimal valorCotizado) {
        this.valorCotizado = valorCotizado;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
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
        hash += (materialesproyectoPK != null ? materialesproyectoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materialesproyecto)) {
            return false;
        }
        Materialesproyecto other = (Materialesproyecto) object;
        if ((this.materialesproyectoPK == null && other.materialesproyectoPK != null) || (this.materialesproyectoPK != null && !this.materialesproyectoPK.equals(other.materialesproyectoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "entities.Materialesproyecto[ materialesproyectoPK=" + materialesproyectoPK + " ]";
				return materiales.getIdMaterialTipo().getNombre()
						+ " <proy.: " + proyectos.getNombre();
    }
    
}
