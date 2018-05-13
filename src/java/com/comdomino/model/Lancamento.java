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
@Table(name = "lancamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lancamento.findAll", query = "SELECT l FROM Lancamento l")
    , @NamedQuery(name = "Lancamento.findById", query = "SELECT l FROM Lancamento l WHERE l.id = :id")
    , @NamedQuery(name = "Lancamento.findByDescricao", query = "SELECT l FROM Lancamento l WHERE l.descricao = :descricao")
    , @NamedQuery(name = "Lancamento.findByValortotal", query = "SELECT l FROM Lancamento l WHERE l.valortotal = :valortotal")})
public class Lancamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "descricao")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valortotal")
    private Float valortotal;
    @OneToMany(mappedBy = "lancamentoId")
    private Collection<Lancamentofinanceiro> lancamentofinanceiroCollection;
    @JoinColumn(name = "tipolancamento_id", referencedColumnName = "id")
    @ManyToOne
    private Tipolancamento tipolancamentoId;
    @OneToMany(mappedBy = "lancamentoId")
    private Collection<Itemlancamento> itemlancamentoCollection;

    public Lancamento() {
    }

    public Lancamento(Long id) {
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

    public Float getValortotal() {
        return valortotal;
    }

    public void setValortotal(Float valortotal) {
        this.valortotal = valortotal;
    }

    @XmlTransient
    public Collection<Lancamentofinanceiro> getLancamentofinanceiroCollection() {
        return lancamentofinanceiroCollection;
    }

    public void setLancamentofinanceiroCollection(Collection<Lancamentofinanceiro> lancamentofinanceiroCollection) {
        this.lancamentofinanceiroCollection = lancamentofinanceiroCollection;
    }

    public Tipolancamento getTipolancamentoId() {
        return tipolancamentoId;
    }

    public void setTipolancamentoId(Tipolancamento tipolancamentoId) {
        this.tipolancamentoId = tipolancamentoId;
    }

    @XmlTransient
    public Collection<Itemlancamento> getItemlancamentoCollection() {
        return itemlancamentoCollection;
    }

    public void setItemlancamentoCollection(Collection<Itemlancamento> itemlancamentoCollection) {
        this.itemlancamentoCollection = itemlancamentoCollection;
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
        if (!(object instanceof Lancamento)) {
            return false;
        }
        Lancamento other = (Lancamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Lancamento[ id=" + id + " ]";
    }
    
}
