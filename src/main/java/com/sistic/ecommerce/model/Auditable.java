package com.sistic.ecommerce.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> {
    @CreatedBy
    @Column(name = "create_by", nullable = true)
    private U createBy;

    @CreatedDate
    @Column(name = "create_date", nullable = true)
    private Date createDate;

    @LastModifiedBy
    @Column(name = "update_by", nullable = true)
    private U updateBy;

    @LastModifiedDate
    @Column(name = "update_date", nullable = true)
    private Date updateDate;

    public U getCreateBy() {
        return createBy;
    }

    public void setCreateBy(U createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public U getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(U updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}