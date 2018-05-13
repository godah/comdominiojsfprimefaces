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
@Table(name = "condominio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Condominio.findAll", query = "SELECT c FROM Condominio c")
    , @NamedQuery(name = "Condominio.findById", query = "SELECT c FROM Condominio c WHERE c.id = :id")
    , @NamedQuery(name = "Condominio.findByBairro", query = "SELECT c FROM Condominio c WHERE c.bairro = :bairro")
    , @NamedQuery(name = "Condominio.findByCep", query = "SELECT c FROM Condominio c WHERE c.cep = :cep")
    , @NamedQuery(name = "Condominio.findByCidade", query = "SELECT c FROM Condominio c WHERE c.cidade = :cidade")
    , @NamedQuery(name = "Condominio.findByComplemento", query = "SELECT c FROM Condominio c WHERE c.complemento = :complemento")
    , @NamedQuery(name = "Condominio.findByLogradouro", query = "SELECT c FROM Condominio c WHERE c.logradouro = :logradouro")
    , @NamedQuery(name = "Condominio.findByNome", query = "SELECT c FROM Condominio c WHERE c.nome = :nome")
    , @NamedQuery(name = "Condominio.findByNumero", query = "SELECT c FROM Condominio c WHERE c.numero = :numero")})
public class Condominio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "bairro")
    private String bairro;
    @Size(max = 255)
    @Column(name = "cep")
    private String cep;
    @Size(max = 255)
    @Column(name = "cidade")
    private String cidade;
    @Size(max = 255)
    @Column(name = "complemento")
    private String complemento;
    @Size(max = 255)
    @Column(name = "logradouro")
    private String logradouro;
    @Size(max = 255)
    @Column(name = "nome")
    private String nome;
    @Size(max = 255)
    @Column(name = "numero")
    private String numero;
    @OneToMany(mappedBy = "condominioId")
    private Collection<Agendamentolazer> agendamentolazerCollection;
    @OneToMany(mappedBy = "condominioId")
    private Collection<Financeiro> financeiroCollection;
    @OneToMany(mappedBy = "condominioId")
    private Collection<Mensagem> mensagemCollection;
    @OneToMany(mappedBy = "condominioId")
    private Collection<Grupo> grupoCollection;
    @JoinColumn(name = "sindico_id", referencedColumnName = "id")
    @ManyToOne
    private Pessoa sindicoId;
    @OneToMany(mappedBy = "condominioId")
    private Collection<Notificacao> notificacaoCollection;
    @OneToMany(mappedBy = "condominioId")
    private Collection<Pessoacondominio> pessoacondominioCollection;

    public Condominio() {
    }

    public Condominio(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @XmlTransient
    public Collection<Agendamentolazer> getAgendamentolazerCollection() {
        return agendamentolazerCollection;
    }

    public void setAgendamentolazerCollection(Collection<Agendamentolazer> agendamentolazerCollection) {
        this.agendamentolazerCollection = agendamentolazerCollection;
    }

    @XmlTransient
    public Collection<Financeiro> getFinanceiroCollection() {
        return financeiroCollection;
    }

    public void setFinanceiroCollection(Collection<Financeiro> financeiroCollection) {
        this.financeiroCollection = financeiroCollection;
    }

    @XmlTransient
    public Collection<Mensagem> getMensagemCollection() {
        return mensagemCollection;
    }

    public void setMensagemCollection(Collection<Mensagem> mensagemCollection) {
        this.mensagemCollection = mensagemCollection;
    }

    @XmlTransient
    public Collection<Grupo> getGrupoCollection() {
        return grupoCollection;
    }

    public void setGrupoCollection(Collection<Grupo> grupoCollection) {
        this.grupoCollection = grupoCollection;
    }

    public Pessoa getSindicoId() {
        return sindicoId;
    }

    public void setSindicoId(Pessoa sindicoId) {
        this.sindicoId = sindicoId;
    }

    @XmlTransient
    public Collection<Notificacao> getNotificacaoCollection() {
        return notificacaoCollection;
    }

    public void setNotificacaoCollection(Collection<Notificacao> notificacaoCollection) {
        this.notificacaoCollection = notificacaoCollection;
    }

    @XmlTransient
    public Collection<Pessoacondominio> getPessoacondominioCollection() {
        return pessoacondominioCollection;
    }

    public void setPessoacondominioCollection(Collection<Pessoacondominio> pessoacondominioCollection) {
        this.pessoacondominioCollection = pessoacondominioCollection;
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
        if (!(object instanceof Condominio)) {
            return false;
        }
        Condominio other = (Condominio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Condominio[ id=" + id + " ]";
    }
    
}
