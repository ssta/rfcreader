/*
 * The MIT License
 *
 * Copyright 2014 Stephen Stafford.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.clothcat.rfcreader.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ssta
 */
@Entity
@Table(name = "RFC_INDEX")
public class RFC_INDEX implements Serializable {

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
    private Integer id;
    private String title;
    private String issueDate;
    private String status;
    private String obsoletes;
    private String obsoletedBy;
    private String updatedBy;
    private String updates;
    private String also;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        if (!(object instanceof RFC_INDEX)) {
            return false;
        }
        RFC_INDEX other = (RFC_INDEX) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.clothcat.rfcreader.entities.RFC_INDEX[ id=" + getId() + " ]";
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the issueDate
     */
    public String getIssueDate() {
        return issueDate;
    }

    /**
     * @param issueDate the issueDate to set
     */
    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the obsoletes
     */
    public String getObsoletes() {
        return obsoletes;
    }

    /**
     * @param obsoletes the obsoletes to set
     */
    public void setObsoletes(String obsoletes) {
        this.obsoletes = obsoletes;
    }

    /**
     * @return the obsoletedBy
     */
    public String getObsoletedBy() {
        return obsoletedBy;
    }

    /**
     * @param obsoletedBy the obsoletedBy to set
     */
    public void setObsoletedBy(String obsoletedBy) {
        this.obsoletedBy = obsoletedBy;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updates
     */
    public String getUpdates() {
        return updates;
    }

    /**
     * @param updates the updates to set
     */
    public void setUpdates(String updates) {
        this.updates = updates;
    }

    /**
     * @return the also
     */
    public String getAlso() {
        return also;
    }

    /**
     * @param also the also to set
     */
    public void setAlso(String also) {
        this.also = also;
    }
}
