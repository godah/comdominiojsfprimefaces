/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oscelulares.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luciano
 */
@Entity
@Table(name = "serviceorder")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Serviceorder.findAll", query = "SELECT s FROM Serviceorder s"),
    @NamedQuery(name = "Serviceorder.findById", query = "SELECT s FROM Serviceorder s WHERE s.id = :id"),
    @NamedQuery(name = "Serviceorder.findByCode", query = "SELECT s FROM Serviceorder s WHERE s.code = :code"),
    @NamedQuery(name = "Serviceorder.findByDate", query = "SELECT s FROM Serviceorder s WHERE s.date = :date"),
    @NamedQuery(name = "Serviceorder.findByImei", query = "SELECT s FROM Serviceorder s WHERE s.imei = :imei"),
    @NamedQuery(name = "Serviceorder.findByLockpasswd", query = "SELECT s FROM Serviceorder s WHERE s.lockpasswd = :lockpasswd"),
    @NamedQuery(name = "Serviceorder.findByBatery", query = "SELECT s FROM Serviceorder s WHERE s.batery = :batery"),
    @NamedQuery(name = "Serviceorder.findByBaterycode", query = "SELECT s FROM Serviceorder s WHERE s.baterycode = :baterycode"),
    @NamedQuery(name = "Serviceorder.findByWorking", query = "SELECT s FROM Serviceorder s WHERE s.working = :working"),
    @NamedQuery(name = "Serviceorder.findByChip", query = "SELECT s FROM Serviceorder s WHERE s.chip = :chip"),
    @NamedQuery(name = "Serviceorder.findByCharger", query = "SELECT s FROM Serviceorder s WHERE s.charger = :charger"),
    @NamedQuery(name = "Serviceorder.findBySdcard", query = "SELECT s FROM Serviceorder s WHERE s.sdcard = :sdcard"),
    @NamedQuery(name = "Serviceorder.findByNote", query = "SELECT s FROM Serviceorder s WHERE s.note = :note"),
    @NamedQuery(name = "Serviceorder.findByCwCam", query = "SELECT s FROM Serviceorder s WHERE s.cwCam = :cwCam"),
    @NamedQuery(name = "Serviceorder.findByCwAudio", query = "SELECT s FROM Serviceorder s WHERE s.cwAudio = :cwAudio"),
    @NamedQuery(name = "Serviceorder.findByCwBell", query = "SELECT s FROM Serviceorder s WHERE s.cwBell = :cwBell"),
    @NamedQuery(name = "Serviceorder.findByCwMicrofone", query = "SELECT s FROM Serviceorder s WHERE s.cwMicrofone = :cwMicrofone"),
    @NamedQuery(name = "Serviceorder.findByCwDisplay", query = "SELECT s FROM Serviceorder s WHERE s.cwDisplay = :cwDisplay"),
    @NamedQuery(name = "Serviceorder.findByCwExternalsignal", query = "SELECT s FROM Serviceorder s WHERE s.cwExternalsignal = :cwExternalsignal"),
    @NamedQuery(name = "Serviceorder.findByCwKeys", query = "SELECT s FROM Serviceorder s WHERE s.cwKeys = :cwKeys"),
    @NamedQuery(name = "Serviceorder.findByCwChargecircuit", query = "SELECT s FROM Serviceorder s WHERE s.cwChargecircuit = :cwChargecircuit"),
    @NamedQuery(name = "Serviceorder.findByScratchedcarcass", query = "SELECT s FROM Serviceorder s WHERE s.scratchedcarcass = :scratchedcarcass"),
    @NamedQuery(name = "Serviceorder.findByBrokencarcass", query = "SELECT s FROM Serviceorder s WHERE s.brokencarcass = :brokencarcass"),
    @NamedQuery(name = "Serviceorder.findByAuthorization", query = "SELECT s FROM Serviceorder s WHERE s.authorization = :authorization"),
    @NamedQuery(name = "Serviceorder.findByAuthorizationdate", query = "SELECT s FROM Serviceorder s WHERE s.authorizationdate = :authorizationdate"),
    @NamedQuery(name = "Serviceorder.findByRisk", query = "SELECT s FROM Serviceorder s WHERE s.risk = :risk"),
    @NamedQuery(name = "Serviceorder.findByIssues", query = "SELECT s FROM Serviceorder s WHERE s.issues = :issues"),
    @NamedQuery(name = "Serviceorder.findByResultOk", query = "SELECT s FROM Serviceorder s WHERE s.resultOk = :resultOk"),
    @NamedQuery(name = "Serviceorder.findByResultNotfix", query = "SELECT s FROM Serviceorder s WHERE s.resultNotfix = :resultNotfix"),
    @NamedQuery(name = "Serviceorder.findByDelivered", query = "SELECT s FROM Serviceorder s WHERE s.delivered = :delivered"),
    @NamedQuery(name = "Serviceorder.findByDeliveryforecast", query = "SELECT s FROM Serviceorder s WHERE s.deliveryforecast = :deliveryforecast"),
    @NamedQuery(name = "Serviceorder.findByUndone", query = "SELECT s FROM Serviceorder s WHERE s.undone = :undone"),
    @NamedQuery(name = "Serviceorder.findByAbandoned", query = "SELECT s FROM Serviceorder s WHERE s.abandoned = :abandoned"),
    @NamedQuery(name = "Serviceorder.findByTechnote", query = "SELECT s FROM Serviceorder s WHERE s.technote = :technote"),
    @NamedQuery(name = "Serviceorder.findBySignal", query = "SELECT s FROM Serviceorder s WHERE s.paysignal = :paysignal"),
    @NamedQuery(name = "Serviceorder.findByRest", query = "SELECT s FROM Serviceorder s WHERE s.rest = :rest")})
public class Serviceorder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "code")
    private int code;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Size(max = 15)
    @Column(name = "imei")
    private String imei;
    @Size(max = 100)
    @Column(name = "lockpasswd")
    private String lockpasswd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "batery")
    private boolean batery;
    @Size(max = 100)
    @Column(name = "baterycode")
    private String baterycode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "working")
    private boolean working;
    @Basic(optional = false)
    @NotNull
    @Column(name = "chip")
    private boolean chip;
    @Basic(optional = false)
    @NotNull
    @Column(name = "charger")
    private boolean charger;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sdcard")
    private boolean sdcard;
    @Size(max = 200)
    @Column(name = "note")
    private String note;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cw_cam")
    private boolean cwCam;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cw_audio")
    private boolean cwAudio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cw_bell")
    private boolean cwBell;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cw_microfone")
    private boolean cwMicrofone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cw_display")
    private boolean cwDisplay;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cw_externalsignal")
    private boolean cwExternalsignal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cw_keys")
    private boolean cwKeys;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cw_chargecircuit")
    private boolean cwChargecircuit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scratchedcarcass")
    private boolean scratchedcarcass;
    @Basic(optional = false)
    @NotNull
    @Column(name = "brokencarcass")
    private boolean brokencarcass;
    @Column(name = "authorization")
    private Character authorization;
    @Column(name = "authorizationdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date authorizationdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "risk")
    private boolean risk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "issues")
    private String issues;
    @Column(name = "result_ok")
    private Boolean resultOk;
    @Column(name = "result_notfix")
    private Boolean resultNotfix;
    @Basic(optional = false)
    @NotNull
    @Column(name = "delivered")
    private boolean delivered;
    @Column(name = "deliveryforecast")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryforecast;
    @Basic(optional = false)
    @NotNull
    @Column(name = "undone")
    private boolean undone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "abandoned")
    private boolean abandoned;
    @Size(max = 300)
    @Column(name = "technote")
    private String technote;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "paysignal")
    private BigDecimal paysignal;
    @Column(name = "rest")
    private BigDecimal rest;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceorderId")
    private Collection<Budget> budgetCollection;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Client clientId;
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Model modelId;
    @JoinColumn(name = "carrierservice_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Carrierservice carrierserviceId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public Serviceorder() {
    }

    public Serviceorder(Integer id) {
        this.id = id;
    }

    public Serviceorder(Integer id, int code, boolean batery, boolean working, boolean chip, boolean charger, boolean sdcard, boolean cwCam, boolean cwAudio, boolean cwBell, boolean cwMicrofone, boolean cwDisplay, boolean cwExternalsignal, boolean cwKeys, boolean cwChargecircuit, boolean scratchedcarcass, boolean brokencarcass, boolean risk, String issues, boolean delivered, boolean undone, boolean abandoned) {
        this.id = id;
        this.code = code;
        this.batery = batery;
        this.working = working;
        this.chip = chip;
        this.charger = charger;
        this.sdcard = sdcard;
        this.cwCam = cwCam;
        this.cwAudio = cwAudio;
        this.cwBell = cwBell;
        this.cwMicrofone = cwMicrofone;
        this.cwDisplay = cwDisplay;
        this.cwExternalsignal = cwExternalsignal;
        this.cwKeys = cwKeys;
        this.cwChargecircuit = cwChargecircuit;
        this.scratchedcarcass = scratchedcarcass;
        this.brokencarcass = brokencarcass;
        this.risk = risk;
        this.issues = issues;
        this.delivered = delivered;
        this.undone = undone;
        this.abandoned = abandoned;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLockpasswd() {
        return lockpasswd;
    }

    public void setLockpasswd(String lockpasswd) {
        this.lockpasswd = lockpasswd;
    }

    public boolean getBatery() {
        return batery;
    }

    public void setBatery(boolean batery) {
        this.batery = batery;
    }

    public String getBaterycode() {
        return baterycode;
    }

    public void setBaterycode(String baterycode) {
        this.baterycode = baterycode;
    }

    public boolean getWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public boolean getChip() {
        return chip;
    }

    public void setChip(boolean chip) {
        this.chip = chip;
    }

    public boolean getCharger() {
        return charger;
    }

    public void setCharger(boolean charger) {
        this.charger = charger;
    }

    public boolean getSdcard() {
        return sdcard;
    }

    public void setSdcard(boolean sdcard) {
        this.sdcard = sdcard;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean getCwCam() {
        return cwCam;
    }

    public void setCwCam(boolean cwCam) {
        this.cwCam = cwCam;
    }

    public boolean getCwAudio() {
        return cwAudio;
    }

    public void setCwAudio(boolean cwAudio) {
        this.cwAudio = cwAudio;
    }

    public boolean getCwBell() {
        return cwBell;
    }

    public void setCwBell(boolean cwBell) {
        this.cwBell = cwBell;
    }

    public boolean getCwMicrofone() {
        return cwMicrofone;
    }

    public void setCwMicrofone(boolean cwMicrofone) {
        this.cwMicrofone = cwMicrofone;
    }

    public boolean getCwDisplay() {
        return cwDisplay;
    }

    public void setCwDisplay(boolean cwDisplay) {
        this.cwDisplay = cwDisplay;
    }

    public boolean getCwExternalsignal() {
        return cwExternalsignal;
    }

    public void setCwExternalsignal(boolean cwExternalsignal) {
        this.cwExternalsignal = cwExternalsignal;
    }

    public boolean getCwKeys() {
        return cwKeys;
    }

    public void setCwKeys(boolean cwKeys) {
        this.cwKeys = cwKeys;
    }

    public boolean getCwChargecircuit() {
        return cwChargecircuit;
    }

    public void setCwChargecircuit(boolean cwChargecircuit) {
        this.cwChargecircuit = cwChargecircuit;
    }

    public boolean getScratchedcarcass() {
        return scratchedcarcass;
    }

    public void setScratchedcarcass(boolean scratchedcarcass) {
        this.scratchedcarcass = scratchedcarcass;
    }

    public boolean getBrokencarcass() {
        return brokencarcass;
    }

    public void setBrokencarcass(boolean brokencarcass) {
        this.brokencarcass = brokencarcass;
    }

    public Character getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Character authorization) {
        this.authorization = authorization;
    }

    public Date getAuthorizationdate() {
        return authorizationdate;
    }

    public void setAuthorizationdate(Date authorizationdate) {
        this.authorizationdate = authorizationdate;
    }

    public boolean getRisk() {
        return risk;
    }

    public void setRisk(boolean risk) {
        this.risk = risk;
    }

    public String getIssues() {
        return issues;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }

    public Boolean getResultOk() {
        return resultOk;
    }

    public void setResultOk(Boolean resultOk) {
        this.resultOk = resultOk;
    }

    public Boolean getResultNotfix() {
        return resultNotfix;
    }

    public void setResultNotfix(Boolean resultNotfix) {
        this.resultNotfix = resultNotfix;
    }

    public boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public Date getDeliveryforecast() {
        return deliveryforecast;
    }

    public void setDeliveryforecast(Date deliveryforecast) {
        this.deliveryforecast = deliveryforecast;
    }

    public boolean getUndone() {
        return undone;
    }

    public void setUndone(boolean undone) {
        this.undone = undone;
    }

    public boolean getAbandoned() {
        return abandoned;
    }

    public void setAbandoned(boolean abandoned) {
        this.abandoned = abandoned;
    }

    public String getTechnote() {
        return technote;
    }

    public void setTechnote(String technote) {
        this.technote = technote;
    }

    public BigDecimal getSignal() {
        return paysignal;
    }

    public void setSignal(BigDecimal paysignal) {
        this.paysignal = paysignal;
    }

    public BigDecimal getRest() {
        return rest;
    }

    public void setRest(BigDecimal rest) {
        this.rest = rest;
    }

    @XmlTransient
    public Collection<Budget> getBudgetCollection() {
        return budgetCollection;
    }

    public void setBudgetCollection(Collection<Budget> budgetCollection) {
        this.budgetCollection = budgetCollection;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public Model getModelId() {
        return modelId;
    }

    public void setModelId(Model modelId) {
        this.modelId = modelId;
    }

    public Carrierservice getCarrierserviceId() {
        return carrierserviceId;
    }

    public void setCarrierserviceId(Carrierservice carrierserviceId) {
        this.carrierserviceId = carrierserviceId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof Serviceorder)) {
            return false;
        }
        Serviceorder other = (Serviceorder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oscelulares.model.Serviceorder[ id=" + id + " ]";
    }
    
}
