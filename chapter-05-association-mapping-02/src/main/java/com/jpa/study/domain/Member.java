package com.jpa.study.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Member {
    
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;
    
   //좋은 설계는 아니지만 그냥 JPA 학습용도 - 양방향 매핑과 편의주의적 설계는 되도록 지양하자!!
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>(); 
    
    public Member() {}
    
    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        order.setMember(this); //양방향 객체관계를 맺어줌
        this.orders.add(order);
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
}
