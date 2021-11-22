package com.java017.tripblog.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class FreeTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer freeTagId;

    private String freeTagName;

    public Integer getFreeTagId() {
        return freeTagId;
    }

    public void setFreeTagId(Integer freeTagId) {
        this.freeTagId = freeTagId;
    }

    public String getFreeTagName() {
        return freeTagName;
    }

    public void setFreeTagName(String freeTagName) {
        this.freeTagName = freeTagName;
    }

    @Override
    public String toString() {
        return "FreeTag{" +
                "freeTagId=" + freeTagId +
                ", freeTagName='" + freeTagName + '\'' +
                '}';
    }

}