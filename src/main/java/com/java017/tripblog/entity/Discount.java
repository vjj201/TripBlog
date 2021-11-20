package com.java017.tripblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author YuCheng
 * @date 2021/11/20 - 上午 11:39
 */

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //優惠名稱
    private String title;
    //詳情說明
    private String detail;

    //折數
    private int discountNumber;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Temporal(TemporalType.DATE)
    private Date expiredTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getDiscountNumber() {
        return discountNumber;
    }

    public void setDiscountNumber(int discountNumber) {
        this.discountNumber = discountNumber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public String toString() {
        return "discount{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", discountNumber=" + discountNumber +
                ", createDate=" + createDate +
                ", expiredTime=" + expiredTime +
                '}';
    }
}
