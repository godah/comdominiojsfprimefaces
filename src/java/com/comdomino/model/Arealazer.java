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
 * @author lobo
 */
@Entity
@Table(name = "arealazer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Arealazer.findAll", query = "SELECT a FROM Arealazer a")
    , @NamedQuery(name = "Arealazer.findById", query = "SELECT a FROM Arealazer a WHERE a.id = :id")
    , @NamedQuery(name = "Arealazer.findByArea", query = "SELECT a FROM Arealazer a WHERE a.area = :area")
    , @NamedQuery(name = "Arealazer.findByDescricao", query = "SELECT a FROM Arealazer a WHERE a.descricao = :descricao")})
public class Arealazer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "area")
    private String area;
    @Size(max = 255)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(mappedBy = "areadelazerId")
    private Collection<Agendamentolazer> agendamentolazerCollection;
    @JoinColumn(name = "tipoarea_id", referencedColumnName = "id")
    @ManyToOne
    private Tipoarealazer tipoareaId;

    public Arealazer() {
    }

    public Arealazer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public Collection<Agendamentolazer> getAgendamentolazerCollection() {
        return agendamentolazerCollection;
    }

    public void setAgendamentolazerCollection(Collection<Agendamentolazer> agendamentolazerCollection) {
        this.agendamentolazerCollection = agendamentolazerCollection;
    }

    public Tipoarealazer getTipoareaId() {
        return tipoareaId;
    }

    public void setTipoareaId(Tipoarealazer tipoareaId) {
        this.tipoareaId = tipoareaId;
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
        if (!(object instanceof Arealazer)) {
            return false;
        }
        Arealazer other = (Arealazer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Arealazer[ id=" + id + " ]";
    }
    
}
