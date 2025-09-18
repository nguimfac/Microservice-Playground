package com.playground.inventory_service.model;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
//Not an entity in itself but these fields just need to be inherited@MappedSuperclass
@MappedSuperclass
// Define a JPA listener that will automatically populate fields on insert and update
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {
    @CreatedDate
    private Date createDate;

    @LastModifiedDate
    private Date updatedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;
}
