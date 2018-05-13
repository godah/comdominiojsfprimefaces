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
@Table(name = "agendamentolazer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agendamentolazer.findAll", query = "SELECT a FROM Agendamentolazer a")
    , @NamedQuery(name = "Agendamentolazer.findById", query = "SELECT a FROM Agendamentolazer a WHERE a.id = :id")
    , @NamedQuery(name = "Agendamentolazer.findByAprovacao", query = "SELECT a FROM Agendamentolazer a WHERE a.aprovacao = :aprovacao")
    , @NamedQuery(name = "Agendamentolazer.findByDataaprovacao", query = "SELECT a FROM Agendamentolazer a WHERE a.dataaprovacao = :dataaprovacao")
    , @NamedQuery(name = "Agendamentolazer.findByEvento", query = "SELECT a FROM Agendamentolazer a WHERE a.evento = :evento")
    , @NamedQuery(name = "Agendamentolazer.findByFim", query = "SELECT a FROM Agendamentolazer a WHERE a.fim = :fim")
    , @NamedQuery(name = "Agendamentolazer.findByInicio", query = "SELECT a FROM Agendamentolazer a WHERE a.inicio = :inicio")})
public class Agendamentolazer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "aprovacao")
    private Boolean aprovacao;
    @Size(max = 255)
    @Column(name = "dataaprovacao")
    private String dataaprovacao;
    @Size(max = 255)
    @Column(name = "evento")
    private String evento;
    @Size(max = 255)
    @Column(name = "fim")
    private String fim;
    @Size(max = 255)
    @Column(name = "inicio")
    private String inicio;
    @JoinColumn(name = "areadelazer_id", referencedColumnName = "id")
    @ManyToOne
    private Arealazer areadelazerId;
    @JoinColumn(name = "condominio_id", referencedColumnName = "id")
    @ManyToOne
    private Condominio condominioId;
    @JoinColumn(name = "pessoa_id", referencedColumnName = "id")
    @ManyToOne
    private Pessoa pessoaId;

    public Agendamentolazer() {
    }

    public Agendamentolazer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAprovacao() {
        return aprovacao;
    }

    public void setAprovacao(Boolean aprovacao) {
        this.aprovacao = aprovacao;
    }

    public String getDataaprovacao() {
        return dataaprovacao;
    }

    public void setDataaprovacao(String dataaprovacao) {
        this.dataaprovacao = dataaprovacao;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public Arealazer getAreadelazerId() {
        return areadelazerId;
    }

    public void setAreadelazerId(Arealazer areadelazerId) {
        this.areadelazerId = areadelazerId;
    }

    public Condominio getCondominioId() {
        return condominioId;
    }

    public void setCondominioId(Condominio condominioId) {
        this.condominioId = condominioId;
    }

    public Pessoa getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Pessoa pessoaId) {
        this.pessoaId = pessoaId;
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
        if (!(object instanceof Agendamentolazer)) {
            return false;
        }
        Agendamentolazer other = (Agendamentolazer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Agendamentolazer[ id=" + id + " ]";
    }
    
}
