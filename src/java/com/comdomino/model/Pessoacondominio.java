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
@Table(name = "pessoacondominio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pessoacondominio.findAll", query = "SELECT p FROM Pessoacondominio p")
    , @NamedQuery(name = "Pessoacondominio.findByValue", query = "SELECT p FROM Pessoacondominio p WHERE p.value = :value")
    , @NamedQuery(name = "Pessoacondominio.findByDatafim", query = "SELECT p FROM Pessoacondominio p WHERE p.datafim = :datafim")
    , @NamedQuery(name = "Pessoacondominio.findByDatainicio", query = "SELECT p FROM Pessoacondominio p WHERE p.datainicio = :datainicio")})
public class Pessoacondominio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "value")
    private Long value;
    @Size(max = 255)
    @Column(name = "datafim")
    private String datafim;
    @Size(max = 255)
    @Column(name = "datainicio")
    private String datainicio;
    @JoinColumn(name = "condominio_id", referencedColumnName = "id")
    @ManyToOne
    private Condominio condominioId;
    @JoinColumn(name = "pessoa_id", referencedColumnName = "id")
    @ManyToOne
    private Pessoa pessoaId;

    public Pessoacondominio() {
    }

    public Pessoacondominio(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getDatafim() {
        return datafim;
    }

    public void setDatafim(String datafim) {
        this.datafim = datafim;
    }

    public String getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(String datainicio) {
        this.datainicio = datainicio;
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
        hash += (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pessoacondominio)) {
            return false;
        }
        Pessoacondominio other = (Pessoacondominio) object;
        if ((this.value == null && other.value != null) || (this.value != null && !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comdomino.model.Pessoacondominio[ value=" + value + " ]";
    }
    
}
