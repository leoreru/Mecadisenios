/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PC-HP
 */
@Entity
@Table(name = "municipiosdane")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Municipiosdane.findAll", query = "SELECT m FROM Municipiosdane m")
    , @NamedQuery(name = "Municipiosdane.findByCodDep", query = "SELECT m FROM Municipiosdane m WHERE m.municipiosdanePK.codDep = :codDep")
    , @NamedQuery(name = "Municipiosdane.findByCodMun", query = "SELECT m FROM Municipiosdane m WHERE m.municipiosdanePK.codMun = :codMun")
    , @NamedQuery(name = "Municipiosdane.findByNombre", query = "SELECT m FROM Municipiosdane m WHERE m.nombre = :nombre")})
public class Municipiosdane implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MunicipiosdanePK municipiosdanePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "codDep", referencedColumnName = "codDep", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Departamentosdane departamentosdane;
    @OneToMany(mappedBy = "municipiosdane")
    private Collection<Personajuridica> personajuridicaCollection;

    public Municipiosdane() {
    }

    public Municipiosdane(MunicipiosdanePK municipiosdanePK) {
        this.municipiosdanePK = municipiosdanePK;
    }

    public Municipiosdane(MunicipiosdanePK municipiosdanePK, String nombre) {
        this.municipiosdanePK = municipiosdanePK;
        this.nombre = nombre;
    }

    public Municipiosdane(short codDep, short codMun) {
        this.municipiosdanePK = new MunicipiosdanePK(codDep, codMun);
    }

    public MunicipiosdanePK getMunicipiosdanePK() {
        return municipiosdanePK;
    }

    public void setMunicipiosdanePK(MunicipiosdanePK municipiosdanePK) {
        this.municipiosdanePK = municipiosdanePK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Departamentosdane getDepartamentosdane() {
        return departamentosdane;
    }

    public void setDepartamentosdane(Departamentosdane departamentosdane) {
        this.departamentosdane = departamentosdane;
    }

    @XmlTransient
    public Collection<Personajuridica> getPersonajuridicaCollection() {
        return personajuridicaCollection;
    }

    public void setPersonajuridicaCollection(Collection<Personajuridica> personajuridicaCollection) {
        this.personajuridicaCollection = personajuridicaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (municipiosdanePK != null ? municipiosdanePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Municipiosdane)) {
            return false;
        }
        Municipiosdane other = (Municipiosdane) object;
        if ((this.municipiosdanePK == null && other.municipiosdanePK != null) || (this.municipiosdanePK != null && !this.municipiosdanePK.equals(other.municipiosdanePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "entities.Municipiosdane[ municipiosdanePK=" + municipiosdanePK + " ]";
				return nombre;
    }
    
}
