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
@Table(name = "notificacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notificacao.findAll", query = "SELECT n FROM Notificacao n")
    , @NamedQuery(name = "Notificacao.findById", query = "SELECT n FROM Notificacao n WHERE n.id = :id")
    , @NamedQuery(name = "Notificacao.findByData", query = "SELECT n FROM Notificacao n WHERE n.data = :data")
    , @NamedQuery(name = "Notificacao.findByDescricao", query = "SELECT n FROM Notificacao n WHERE n.descricao = :descricao")
    , @NamedQuery(name = "Notificacao.findByTitulo", query = "SELECT n FROM Notificacao n WHERE n.titulo = :titulo")})
public class Notificacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "data")
    private String data;
    @Size(max = 255)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 255)
    @Column(name = "titulo")
    private String titulo;
    @JoinColumn(name = "condominio_id", referencedColumnName = "id")
    @ManyToOne
    private Condominio condominioId;
    @JoinColumn(name = "pessoa_id", referencedColumnName = "id")
    @ManyToOne
    private Pessoa pessoaId;

    public Notificacao() {
    }

    public Notificacao(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
        if (!(object instanceof Notificacao)) {
            return false;
        }
        Notificacao other = (Notificacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Notificacao[ id=" + id + " ]";
    }
    
}
