package com.percyvega.db2process.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

@Entity
@Table(name = "MY_RECORD", schema = "SA")
//@XmlRootElement(name = "MyRecord")
public class MyRecord extends BaseEntity {

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    private String mdn;

    @Column(name = "ORDER_TYPE")
    private String orderType;

    private String response;

    @Column(name = "TRY_COUNT")
    private Long tryCount;
    
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "UPDATE_DATE")
    private Timestamp updateDate;

    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getMdn() {
        return this.mdn;
    }

    public void setMdn(String mdn) {
        this.mdn = mdn;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Long getTryCount() {
        return this.tryCount;
    }

    public void setTryCount(Long tryCount) {
        this.tryCount = tryCount;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
        setUpdateDate();
    }

    public Timestamp getUpdateDate() {
        return this.updateDate;
    }

    private void setUpdateDate() {
        this.updateDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [mdn=" + mdn + ", objid=" + objid + ", orderType=" + orderType + ", response=" + response
                + ", tryCount=" + tryCount + ", status=" + status + ", updateDate=" + updateDate + "]";
    }

    public enum Status {
        QUEUED, PROCESSING, PROCESSED, PICKED_UP
    }

}
