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
@Table(name = "unidade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unidade.findAll", query = "SELECT u FROM Unidade u")
    , @NamedQuery(name = "Unidade.findById", query = "SELECT u FROM Unidade u WHERE u.id = :id")
    , @NamedQuery(name = "Unidade.findByDescricao", query = "SELECT u FROM Unidade u WHERE u.descricao = :descricao")})
public class Unidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "pessoaproprietario_id", referencedColumnName = "id")
    @ManyToOne
    private Pessoa pessoaproprietarioId;
    @JoinColumn(name = "pessoaresponsavel_id", referencedColumnName = "id")
    @ManyToOne
    private Pessoa pessoaresponsavelId;
    @JoinColumn(name = "tipounidade_id", referencedColumnName = "id")
    @ManyToOne
    private Tipounidade tipounidadeId;
    @OneToMany(mappedBy = "unidadeidId")
    private Collection<Vagasdisponivel> vagasdisponivelCollection;

    public Unidade() {
    }

    public Unidade(Long id) {
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

    public Pessoa getPessoaproprietarioId() {
        return pessoaproprietarioId;
    }

    public void setPessoaproprietarioId(Pessoa pessoaproprietarioId) {
        this.pessoaproprietarioId = pessoaproprietarioId;
    }

    public Pessoa getPessoaresponsavelId() {
        return pessoaresponsavelId;
    }

    public void setPessoaresponsavelId(Pessoa pessoaresponsavelId) {
        this.pessoaresponsavelId = pessoaresponsavelId;
    }

    public Tipounidade getTipounidadeId() {
        return tipounidadeId;
    }

    public void setTipounidadeId(Tipounidade tipounidadeId) {
        this.tipounidadeId = tipounidadeId;
    }

    @XmlTransient
    public Collection<Vagasdisponivel> getVagasdisponivelCollection() {
        return vagasdisponivelCollection;
    }

    public void setVagasdisponivelCollection(Collection<Vagasdisponivel> vagasdisponivelCollection) {
        this.vagasdisponivelCollection = vagasdisponivelCollection;
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
        if (!(object instanceof Unidade)) {
            return false;
        }
        Unidade other = (Unidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Unidade[ id=" + id + " ]";
    }
    
}
