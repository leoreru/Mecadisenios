/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "tipogasto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipogasto.findAll", query = "SELECT t FROM Tipogasto t")
    , @NamedQuery(name = "Tipogasto.findById", query = "SELECT t FROM Tipogasto t WHERE t.id = :id")
    , @NamedQuery(name = "Tipogasto.findByNombre", query = "SELECT t FROM Tipogasto t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Tipogasto.findByDescripcion", query = "SELECT t FROM Tipogasto t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "Tipogasto.findByValor", query = "SELECT t FROM Tipogasto t WHERE t.valor = :valor")})
public class Tipogasto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private BigDecimal valor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipogasto")
    private Collection<Otrosgastosproyecto> otrosgastosproyectoCollection;

    public Tipogasto() {
    }

    public Tipogasto(Short id) {
        this.id = id;
    }

    public Tipogasto(Short id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @XmlTransient
    public Collection<Otrosgastosproyecto> getOtrosgastosproyectoCollection() {
        return otrosgastosproyectoCollection;
    }

    public void setOtrosgastosproyectoCollection(Collection<Otrosgastosproyecto> otrosgastosproyectoCollection) {
        this.otrosgastosproyectoCollection = otrosgastosproyectoCollection;
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
        if (!(object instanceof Tipogasto)) {
            return false;
        }
        Tipogasto other = (Tipogasto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "entities.Tipogasto[ id=" + id + " ]";
				return nombre;
    }
    
}
