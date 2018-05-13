/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comdomino.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lobo
 */
@Entity
@Table(name = "itemlancamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Itemlancamento.findAll", query = "SELECT i FROM Itemlancamento i")
    , @NamedQuery(name = "Itemlancamento.findById", query = "SELECT i FROM Itemlancamento i WHERE i.id = :id")
    , @NamedQuery(name = "Itemlancamento.findByDescricao", query = "SELECT i FROM Itemlancamento i WHERE i.descricao = :descricao")
    , @NamedQuery(name = "Itemlancamento.findByQuantidade", query = "SELECT i FROM Itemlancamento i WHERE i.quantidade = :quantidade")
    , @NamedQuery(name = "Itemlancamento.findByValorunit", query = "SELECT i FROM Itemlancamento i WHERE i.valorunit = :valorunit")})
public class Itemlancamento implements Serializable {

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
    @Column(name = "quantidade")
    private Float quantidade;
    @Column(name = "valorunit")
    private Float valorunit;
    @JoinColumn(name = "lancamento_id", referencedColumnName = "id")
    @ManyToOne
    private Lancamento lancamentoId;

    public Itemlancamento() {
    }

    public Itemlancamento(Long id) {
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

    public Float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Float quantidade) {
        this.quantidade = quantidade;
    }

    public Float getValorunit() {
        return valorunit;
    }

    public void setValorunit(Float valorunit) {
        this.valorunit = valorunit;
    }

    public Lancamento getLancamentoId() {
        return lancamentoId;
    }

    public void setLancamentoId(Lancamento lancamentoId) {
        this.lancamentoId = lancamentoId;
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
        if (!(object instanceof Itemlancamento)) {
            return false;
        }
        Itemlancamento other = (Itemlancamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Itemlancamento[ id=" + id + " ]";
    }
    
}
