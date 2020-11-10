package com.jpa.study.domain;

import javax.persistence.Embeddable;

/**
 * 값 비교를 위한 equals 메소드 재정의
 * equals 메소드 처리시 여기서는 필드에 직접 접근 보다는
 * getter메서드를 사용해서 접근해서 처리할 것 
 * 이유는 프록시 객체 때문이다.
 */
@Embeddable 
public class Address  {
    private String city;
    private String street;
    private String zipcode;
    
    public Address() {} 
    
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getStreet() == null) ? 0 : getStreet().hashCode());
        result = prime * result + ((getZipcode() == null) ? 0 : getZipcode().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (getCity() == null) {
            if (other.getCity() != null)
                return false;
        } else if (!getCity().equals(other.getCity()))
            return false;
        if (getStreet() == null) {
            if (other.getStreet() != null)
                return false;
        } else if (!getStreet().equals(other.getStreet()))
            return false;
        if (getZipcode() == null) {
            if (other.getZipcode() != null)
                return false;
        } else if (!getZipcode().equals(other.getZipcode()))
            return false;
        return true;
    }
    
    
}
