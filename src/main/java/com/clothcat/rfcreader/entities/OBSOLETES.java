/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.clothcat.rfcreader.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ssta
 */
@Entity
@Table(name = "OBSOLETES")
public class OBSOLETES implements Serializable {
    private static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long rfcId;
    private long obsoletesId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OBSOLETES)) {
            return false;
        }
        OBSOLETES other = (OBSOLETES) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.clothcat.rfcreader.entities.OBSOLETES[ id=" + getId() + " ]";
    }

    /**
     * @return the rfcId
     */
    public long getRfcId() {
        return rfcId;
    }

    /**
     * @param rfcId the rfcId to set
     */
    public void setRfcId(long rfcId) {
        this.rfcId = rfcId;
    }

    /**
     * @return the obsoletesId
     */
    public long getObsoletesId() {
        return obsoletesId;
    }

    /**
     * @param obsoletesId the obsoletesId to set
     */
    public void setObsoletesId(long obsoletesId) {
        this.obsoletesId = obsoletesId;
    }
    
}