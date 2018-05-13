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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lobo
 */
@Entity
@Table(name = "vagasdisponivel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vagasdisponivel.findAll", query = "SELECT v FROM Vagasdisponivel v")
    , @NamedQuery(name = "Vagasdisponivel.findById", query = "SELECT v FROM Vagasdisponivel v WHERE v.id = :id")
    , @NamedQuery(name = "Vagasdisponivel.findByQuantidade", query = "SELECT v FROM Vagasdisponivel v WHERE v.quantidade = :quantidade")
    , @NamedQuery(name = "Vagasdisponivel.findByValor", query = "SELECT v FROM Vagasdisponivel v WHERE v.valor = :valor")})
public class Vagasdisponivel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "quantidade")
    private Integer quantidade;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;
    @JoinColumn(name = "unidadeid_id", referencedColumnName = "id")
    @ManyToOne
    private Unidade unidadeidId;

    public Vagasdisponivel() {
    }

    public Vagasdisponivel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Unidade getUnidadeidId() {
        return unidadeidId;
    }

    public void setUnidadeidId(Unidade unidadeidId) {
        this.unidadeidId = unidadeidId;
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
        if (!(object instanceof Vagasdisponivel)) {
            return false;
        }
        Vagasdisponivel other = (Vagasdisponivel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Vagasdisponivel[ id=" + id + " ]";
    }
    
}
