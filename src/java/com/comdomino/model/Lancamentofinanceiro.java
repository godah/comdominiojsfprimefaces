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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lobo
 */
@Entity
@Table(name = "lancamentofinanceiro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lancamentofinanceiro.findAll", query = "SELECT l FROM Lancamentofinanceiro l")
    , @NamedQuery(name = "Lancamentofinanceiro.findById", query = "SELECT l FROM Lancamentofinanceiro l WHERE l.id = :id")
    , @NamedQuery(name = "Lancamentofinanceiro.findByDatalancamento", query = "SELECT l FROM Lancamentofinanceiro l WHERE l.datalancamento = :datalancamento")
    , @NamedQuery(name = "Lancamentofinanceiro.findByDescricao", query = "SELECT l FROM Lancamentofinanceiro l WHERE l.descricao = :descricao")})
public class Lancamentofinanceiro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "datalancamento")
    private String datalancamento;
    @Size(max = 255)
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "financeiro_id", referencedColumnName = "id")
    @ManyToOne
    private Financeiro financeiroId;
    @JoinColumn(name = "lancamento_id", referencedColumnName = "id")
    @ManyToOne
    private Lancamento lancamentoId;
    @JoinColumn(name = "pessoa_id", referencedColumnName = "id")
    @ManyToOne
    private Pessoa pessoaId;
    @JoinColumn(name = "servico_id", referencedColumnName = "id")
    @ManyToOne
    private Servico servicoId;

    public Lancamentofinanceiro() {
    }

    public Lancamentofinanceiro(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatalancamento() {
        return datalancamento;
    }

    public void setDatalancamento(String datalancamento) {
        this.datalancamento = datalancamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Financeiro getFinanceiroId() {
        return financeiroId;
    }

    public void setFinanceiroId(Financeiro financeiroId) {
        this.financeiroId = financeiroId;
    }

    public Lancamento getLancamentoId() {
        return lancamentoId;
    }

    public void setLancamentoId(Lancamento lancamentoId) {
        this.lancamentoId = lancamentoId;
    }

    public Pessoa getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Pessoa pessoaId) {
        this.pessoaId = pessoaId;
    }

    public Servico getServicoId() {
        return servicoId;
    }

    public void setServicoId(Servico servicoId) {
        this.servicoId = servicoId;
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
        if (!(object instanceof Lancamentofinanceiro)) {
            return false;
        }
        Lancamentofinanceiro other = (Lancamentofinanceiro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Lancamentofinanceiro[ id=" + id + " ]";
    }
    
}
