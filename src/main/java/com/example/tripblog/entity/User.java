package com.example.tripblog.entity;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author YuCheng
 * @date 2021/9/26 - 下午 10:27
 */

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "user")
public class User {

    //會員編號
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //帳號
    @NotBlank(message = "帳號不能為空值")
    private String account;

    //密碼
    @NotBlank(message = "密碼不能為空值")
    private String password;

    //姓名
    @NotBlank(message = "姓名不能為空值")
    private String name;

    //暱稱
    @NotBlank(message = "暱稱不能為空值")
    private String nickname;

    //性別
    private String gender;

    //生日
//    @DateTimeFormat( pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    //信箱
    private String email;

    //手機
    private Long phone;

    //註冊日期
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date signDate;

    //IV外來鍵
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "iv",referencedColumnName = "id")
    private InitializationVector iv;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public InitializationVector getIv() {
        return iv;
    }

    public void setIv(InitializationVector iv) {
        this.iv = iv;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", signDate=" + signDate +
                ", iv=" + iv +
                '}';
    }
}
