package com.java017.tripblog.vo;

/**
 * @author YuCheng
 * @date 2021/11/6 - 下午 10:34
 */

public class CheckoutSession {
    private String receiver;
    private String location;
    private String city;
    private String district;
    private String address;
    private String deliver;
    private Integer freight;
    private String payment;
    private String cardOwner;
    private String cardNumber;

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        int length = cardNumber.length();
        String suffix = cardNumber.substring(length - 4);
        String prefix = cardNumber.substring(0, length - 4);
        prefix = prefix.replaceAll("[0-9]", "*");
        this.cardNumber = prefix + suffix;
    }

    @Override
    public String toString() {
        return "CheckoutSession{" +
                "receiver='" + receiver + '\'' +
                ", location='" + location + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", deliver='" + deliver + '\'' +
                ", freight=" + freight +
                ", payment='" + payment + '\'' +
                ", cardOwner='" + cardOwner + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
