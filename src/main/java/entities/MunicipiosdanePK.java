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
public class MunicipiosdanePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "codDep")
    private short codDep;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codMun")
    private short codMun;

    public MunicipiosdanePK() {
    }

    public MunicipiosdanePK(short codDep, short codMun) {
        this.codDep = codDep;
        this.codMun = codMun;
    }

    public short getCodDep() {
        return codDep;
    }

    public void setCodDep(short codDep) {
        this.codDep = codDep;
    }

    public short getCodMun() {
        return codMun;
    }

    public void setCodMun(short codMun) {
        this.codMun = codMun;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codDep;
        hash += (int) codMun;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MunicipiosdanePK)) {
            return false;
        }
        MunicipiosdanePK other = (MunicipiosdanePK) object;
        if (this.codDep != other.codDep) {
            return false;
        }
        if (this.codMun != other.codMun) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.MunicipiosdanePK[ codDep=" + codDep + ", codMun=" + codMun + " ]";
    }
    
}
