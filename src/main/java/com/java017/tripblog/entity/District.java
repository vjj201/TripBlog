package com.java017.tripblog.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlID;

/**
 * @author YuCheng
 * @date 2021/11/6 - 下午 02:51
 */

@Entity
@Table(name = "District")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String districtName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", districtName='" + districtName + '\'' +
                '}';
    }
}
