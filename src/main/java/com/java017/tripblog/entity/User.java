package com.java017.tripblog.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author YuCheng
 * @date 2021/9/26 - 下午 10:27
 */

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "User")
public class User{

    //會員編號
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //帳號
    @NotBlank(message = "帳號不能為空值")
    private String username;

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
    @Temporal(TemporalType.DATE)
    private Date birthday;

    //信箱
    private String email;

    //手機
    private String phone;

    //註冊日期
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date signDate;

    //信箱是否已驗證
    private boolean mailVerified;

    //是否有頭貼
    private boolean hasMemberPic;

    //自我介紹外來鍵
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "intro", referencedColumnName = "id")
    private Intro intro;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private ShopCart shopCart;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<ProductOrder> productOrderList = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public boolean isMailVerified() {
        return mailVerified;
    }

    public void setMailVerified(boolean mailVerified) {
        this.mailVerified = mailVerified;
    }

    public Intro getIntro() {
        return intro;
    }

    public void setIntro(Intro intro) {
        this.intro = intro;
    }

    public boolean isHasMemberPic() {
        return hasMemberPic;
    }

    public void setHasMemberPic(boolean hasMemberPic) {
        this.hasMemberPic = hasMemberPic;
    }

    public Set<ProductOrder> getProductOrderList() {
        return productOrderList;
    }

    public void setProductOrderList(Set<ProductOrder> productOrderList) {
        this.productOrderList = productOrderList;
    }

    public ShopCart getShopCart() {
        return shopCart;
    }

    public void setShopCart(ShopCart shopCart) {
        this.shopCart = shopCart;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", signDate=" + signDate +
                ", mailVerified=" + mailVerified +
                ", hasMemberPic=" + hasMemberPic +
                '}';
    }
}
