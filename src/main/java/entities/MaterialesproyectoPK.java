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
public class MaterialesproyectoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idProyecto")
    private int idProyecto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idMaterial")
    private int idMaterial;

    public MaterialesproyectoPK() {
    }

    public MaterialesproyectoPK(int idProyecto, int idMaterial) {
        this.idProyecto = idProyecto;
        this.idMaterial = idMaterial;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
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
        hash += (int) idProyecto;
        hash += (int) idMaterial;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterialesproyectoPK)) {
            return false;
        }
        MaterialesproyectoPK other = (MaterialesproyectoPK) object;
        if (this.idProyecto != other.idProyecto) {
            return false;
        }
        if (this.idMaterial != other.idMaterial) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.MaterialesproyectoPK[ idProyecto=" + idProyecto + ", idMaterial=" + idMaterial + " ]";
    }
    
}
