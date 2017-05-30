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
public class OtrosgastosproyectoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idProyecto")
    private int idProyecto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoGasto")
    private short idTipoGasto;

    public OtrosgastosproyectoPK() {
    }

    public OtrosgastosproyectoPK(int idProyecto, short idTipoGasto) {
        this.idProyecto = idProyecto;
        this.idTipoGasto = idTipoGasto;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public short getIdTipoGasto() {
        return idTipoGasto;
    }

    public void setIdTipoGasto(short idTipoGasto) {
        this.idTipoGasto = idTipoGasto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idProyecto;
        hash += (int) idTipoGasto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtrosgastosproyectoPK)) {
            return false;
        }
        OtrosgastosproyectoPK other = (OtrosgastosproyectoPK) object;
        if (this.idProyecto != other.idProyecto) {
            return false;
        }
        if (this.idTipoGasto != other.idTipoGasto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.OtrosgastosproyectoPK[ idProyecto=" + idProyecto + ", idTipoGasto=" + idTipoGasto + " ]";
    }
    
}
