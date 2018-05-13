/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comdomino.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author lobo
 */
@Entity
@Table(name = "tipoarealazer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoarealazer.findAll", query = "SELECT t FROM Tipoarealazer t")
    , @NamedQuery(name = "Tipoarealazer.findById", query = "SELECT t FROM Tipoarealazer t WHERE t.id = :id")
    , @NamedQuery(name = "Tipoarealazer.findByDescricao", query = "SELECT t FROM Tipoarealazer t WHERE t.descricao = :descricao")})
public class Tipoarealazer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(mappedBy = "tipoareaId")
    private Collection<Arealazer> arealazerCollection;

    public Tipoarealazer() {
    }

    public Tipoarealazer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public Collection<Arealazer> getArealazerCollection() {
        return arealazerCollection;
    }

    public void setArealazerCollection(Collection<Arealazer> arealazerCollection) {
        this.arealazerCollection = arealazerCollection;
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
        if (!(object instanceof Tipoarealazer)) {
            return false;
        }
        Tipoarealazer other = (Tipoarealazer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Tipoarealazer[ id=" + id + " ]";
    }
    
}
