package com.jpa.study.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 고급매핑 실전*
 */
@Entity
public class Delivery {
    
    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;
    
    private String city;
    private String street;
    private String zipcode;
    
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    
    @OneToOne(mappedBy = "delivery")
    private Order order;
    
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        order.setDelivery(this);
        this.order = order;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public DeliveryStatus getStatus() {
        return status;
    }
    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
    
}
