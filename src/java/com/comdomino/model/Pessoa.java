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
@Table(name = "pessoa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pessoa.findAll", query = "SELECT p FROM Pessoa p")
    , @NamedQuery(name = "Pessoa.findById", query = "SELECT p FROM Pessoa p WHERE p.id = :id")
    , @NamedQuery(name = "Pessoa.findByCpfcnpj", query = "SELECT p FROM Pessoa p WHERE p.cpfcnpj = :cpfcnpj")
    , @NamedQuery(name = "Pessoa.findByEmail", query = "SELECT p FROM Pessoa p WHERE p.email = :email")
    , @NamedQuery(name = "Pessoa.findByLogin", query = "SELECT p FROM Pessoa p WHERE p.login = :login")
    , @NamedQuery(name = "Pessoa.findByNome", query = "SELECT p FROM Pessoa p WHERE p.nome = :nome")
    , @NamedQuery(name = "Pessoa.findBySenha", query = "SELECT p FROM Pessoa p WHERE p.senha = :senha")
    , @NamedQuery(name = "Pessoa.findByTelefone", query = "SELECT p FROM Pessoa p WHERE p.telefone = :telefone")
    , @NamedQuery(name = "Pessoa.findByVipate", query = "SELECT p FROM Pessoa p WHERE p.vipate = :vipate")})
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "cpfcnpj")
    private String cpfcnpj;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "login")
    private String login;
    @Size(max = 255)
    @Column(name = "nome")
    private String nome;
    @Size(max = 255)
    @Column(name = "senha")
    private String senha;
    @Size(max = 255)
    @Column(name = "telefone")
    private String telefone;
    @Size(max = 255)
    @Column(name = "vipate")
    private String vipate;
    @OneToMany(mappedBy = "pessoaId")
    private Collection<Lancamentofinanceiro> lancamentofinanceiroCollection;
    @JoinColumn(name = "tipopessoa_id", referencedColumnName = "id")
    @ManyToOne
    private Tipopessoa tipopessoaId;
    @OneToMany(mappedBy = "pessoaId")
    private Collection<Agendamentolazer> agendamentolazerCollection;
    @OneToMany(mappedBy = "subsindicoId")
    private Collection<Grupo> grupoCollection;
    @OneToMany(mappedBy = "pessoaproprietarioId")
    private Collection<Unidade> unidadeCollection;
    @OneToMany(mappedBy = "pessoaresponsavelId")
    private Collection<Unidade> unidadeCollection1;
    @OneToMany(mappedBy = "pessoaId")
    private Collection<Terceiro> terceiroCollection;
    @OneToMany(mappedBy = "sindicoId")
    private Collection<Condominio> condominioCollection;
    @OneToMany(mappedBy = "pessoaId")
    private Collection<Notificacao> notificacaoCollection;
    @OneToMany(mappedBy = "pessoaId")
    private Collection<Pessoacondominio> pessoacondominioCollection;

    public Pessoa() {
    }

    public Pessoa(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getVipate() {
        return vipate;
    }

    public void setVipate(String vipate) {
        this.vipate = vipate;
    }

    @XmlTransient
    public Collection<Lancamentofinanceiro> getLancamentofinanceiroCollection() {
        return lancamentofinanceiroCollection;
    }

    public void setLancamentofinanceiroCollection(Collection<Lancamentofinanceiro> lancamentofinanceiroCollection) {
        this.lancamentofinanceiroCollection = lancamentofinanceiroCollection;
    }

    public Tipopessoa getTipopessoaId() {
        return tipopessoaId;
    }

    public void setTipopessoaId(Tipopessoa tipopessoaId) {
        this.tipopessoaId = tipopessoaId;
    }

    @XmlTransient
    public Collection<Agendamentolazer> getAgendamentolazerCollection() {
        return agendamentolazerCollection;
    }

    public void setAgendamentolazerCollection(Collection<Agendamentolazer> agendamentolazerCollection) {
        this.agendamentolazerCollection = agendamentolazerCollection;
    }

    @XmlTransient
    public Collection<Grupo> getGrupoCollection() {
        return grupoCollection;
    }

    public void setGrupoCollection(Collection<Grupo> grupoCollection) {
        this.grupoCollection = grupoCollection;
    }

    @XmlTransient
    public Collection<Unidade> getUnidadeCollection() {
        return unidadeCollection;
    }

    public void setUnidadeCollection(Collection<Unidade> unidadeCollection) {
        this.unidadeCollection = unidadeCollection;
    }

    @XmlTransient
    public Collection<Unidade> getUnidadeCollection1() {
        return unidadeCollection1;
    }

    public void setUnidadeCollection1(Collection<Unidade> unidadeCollection1) {
        this.unidadeCollection1 = unidadeCollection1;
    }

    @XmlTransient
    public Collection<Terceiro> getTerceiroCollection() {
        return terceiroCollection;
    }

    public void setTerceiroCollection(Collection<Terceiro> terceiroCollection) {
        this.terceiroCollection = terceiroCollection;
    }

    @XmlTransient
    public Collection<Condominio> getCondominioCollection() {
        return condominioCollection;
    }

    public void setCondominioCollection(Collection<Condominio> condominioCollection) {
        this.condominioCollection = condominioCollection;
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
        if (!(object instanceof Pessoa)) {
            return false;
        }
        Pessoa other = (Pessoa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Pessoa[ id=" + id + " ]";
    }
    
}
