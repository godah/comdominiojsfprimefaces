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
@Table(name = "terceiro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terceiro.findAll", query = "SELECT t FROM Terceiro t")
    , @NamedQuery(name = "Terceiro.findById", query = "SELECT t FROM Terceiro t WHERE t.id = :id")
    , @NamedQuery(name = "Terceiro.findByAprovacao", query = "SELECT t FROM Terceiro t WHERE t.aprovacao = :aprovacao")
    , @NamedQuery(name = "Terceiro.findByDescricao", query = "SELECT t FROM Terceiro t WHERE t.descricao = :descricao")
    , @NamedQuery(name = "Terceiro.findByEmail", query = "SELECT t FROM Terceiro t WHERE t.email = :email")
    , @NamedQuery(name = "Terceiro.findByNome", query = "SELECT t FROM Terceiro t WHERE t.nome = :nome")
    , @NamedQuery(name = "Terceiro.findByTelefone", query = "SELECT t FROM Terceiro t WHERE t.telefone = :telefone")})
public class Terceiro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "aprovacao")
    private Boolean aprovacao;
    @Size(max = 255)
    @Column(name = "descricao")
    private String descricao;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "nome")
    private String nome;
    @Size(max = 255)
    @Column(name = "telefone")
    private String telefone;
    @JoinColumn(name = "pessoa_id", referencedColumnName = "id")
    @ManyToOne
    private Pessoa pessoaId;

    public Terceiro() {
    }

    public Terceiro(Long id) {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
        if (!(object instanceof Terceiro)) {
            return false;
        }
        Terceiro other = (Terceiro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Terceiro[ id=" + id + " ]";
    }
    
}
