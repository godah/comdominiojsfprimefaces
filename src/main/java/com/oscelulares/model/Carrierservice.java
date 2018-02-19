/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oscelulares.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author luciano
 */
@Entity
@Table(name = "carrierservice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carrierservice.findAll", query = "SELECT c FROM Carrierservice c"),
    @NamedQuery(name = "Carrierservice.findById", query = "SELECT c FROM Carrierservice c WHERE c.id = :id"),
    @NamedQuery(name = "Carrierservice.findByDescr", query = "SELECT c FROM Carrierservice c WHERE c.descr = :descr")})
public class Carrierservice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descr")
    private String descr;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carrierserviceId")
    private Collection<Serviceorder> serviceorderCollection;

    public Carrierservice() {
    }

    public Carrierservice(Integer id) {
        this.id = id;
    }

    public Carrierservice(Integer id, String descr) {
        this.id = id;
        this.descr = descr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @XmlTransient
    public Collection<Serviceorder> getServiceorderCollection() {
        return serviceorderCollection;
    }

    public void setServiceorderCollection(Collection<Serviceorder> serviceorderCollection) {
        this.serviceorderCollection = serviceorderCollection;
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
        if (!(object instanceof Carrierservice)) {
            return false;
        }
        Carrierservice other = (Carrierservice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oscelulares.model.Carrierservice[ id=" + id + " ]";
    }
    
}
