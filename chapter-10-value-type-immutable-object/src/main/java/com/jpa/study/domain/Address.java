package com.jpa.study.domain;

import javax.persistence.Embeddable;

@Embeddable //임베디드 타입 정의 클래스에 설정
public class Address implements Cloneable {
    private String city;
    private String street;
    private String zipcode;
    
    public Address() {} //기본 생성자 필수
    
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }


    public String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    private void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    private void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public Address clone() throws CloneNotSupportedException {
        Address clone = (Address) super.clone();
        clone.setCity(this.city);
        clone.setStreet(this.street);
        clone.setZipcode(this.zipcode);
        return clone;
    }
}
