package com.company.crm.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;

/**
 * BaseEntity contains all common fields across all entities
 */
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE_TIME")
    private Date createdDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_DATE_TIME")
    private Date modifiedDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    @PrePersist
    public void setCreatedDateTime() {
        this.createdDateTime = Calendar.getInstance().getTime();
    }

    public Date getModifiedDateTime() {
        return modifiedDateTime;
    }

    @PreUpdate
    public void setModifiedDateTime() {
        this.modifiedDateTime = Calendar.getInstance().getTime();
    }

//    public final void setCreatedDateTime(Date createdDateTime) {
//        this.createdDateTime = createdDateTime;
//    }
//
//    public final void setModifiedDateTime(Date modifiedDateTime) {
//        this.modifiedDateTime = modifiedDateTime;
//    }
}
