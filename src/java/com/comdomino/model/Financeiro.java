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
@Table(name = "financeiro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Financeiro.findAll", query = "SELECT f FROM Financeiro f")
    , @NamedQuery(name = "Financeiro.findById", query = "SELECT f FROM Financeiro f WHERE f.id = :id")
    , @NamedQuery(name = "Financeiro.findByDescricao", query = "SELECT f FROM Financeiro f WHERE f.descricao = :descricao")})
public class Financeiro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(mappedBy = "financeiroId")
    private Collection<Lancamentofinanceiro> lancamentofinanceiroCollection;
    @JoinColumn(name = "condominio_id", referencedColumnName = "id")
    @ManyToOne
    private Condominio condominioId;

    public Financeiro() {
    }

    public Financeiro(Long id) {
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
    public Collection<Lancamentofinanceiro> getLancamentofinanceiroCollection() {
        return lancamentofinanceiroCollection;
    }

    public void setLancamentofinanceiroCollection(Collection<Lancamentofinanceiro> lancamentofinanceiroCollection) {
        this.lancamentofinanceiroCollection = lancamentofinanceiroCollection;
    }

    public Condominio getCondominioId() {
        return condominioId;
    }

    public void setCondominioId(Condominio condominioId) {
        this.condominioId = condominioId;
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
        if (!(object instanceof Financeiro)) {
            return false;
        }
        Financeiro other = (Financeiro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Financeiro[ id=" + id + " ]";
    }
    
}
