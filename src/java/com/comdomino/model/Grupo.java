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
@Table(name = "grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g")
    , @NamedQuery(name = "Grupo.findById", query = "SELECT g FROM Grupo g WHERE g.id = :id")
    , @NamedQuery(name = "Grupo.findByDescricao", query = "SELECT g FROM Grupo g WHERE g.descricao = :descricao")})
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "condominio_id", referencedColumnName = "id")
    @ManyToOne
    private Condominio condominioId;
    @JoinColumn(name = "subsindico_id", referencedColumnName = "id")
    @ManyToOne
    private Pessoa subsindicoId;

    public Grupo() {
    }

    public Grupo(Long id) {
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

    public Condominio getCondominioId() {
        return condominioId;
    }

    public void setCondominioId(Condominio condominioId) {
        this.condominioId = condominioId;
    }

    public Pessoa getSubsindicoId() {
        return subsindicoId;
    }

    public void setSubsindicoId(Pessoa subsindicoId) {
        this.subsindicoId = subsindicoId;
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
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Grupo[ id=" + id + " ]";
    }
    
}
