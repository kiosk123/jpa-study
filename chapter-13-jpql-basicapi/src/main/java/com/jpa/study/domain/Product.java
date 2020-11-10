package com.jpa.study.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Product {
    
    @Id @GeneratedValue
    private Long id;
    private String name;
    private Integer price;
    private Integer stockAmount;
    
    @OneToMany(mappedBy = "product")
    private List<Order> orders = new ArrayList<>();
    
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
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Integer getStockAmount() {
        return stockAmount;
    }
    public void setStockAmount(Integer stockAmount) {
        this.stockAmount = stockAmount;
    }
    public List<Order> getOrders() {
        return orders;
    }
    public void addOrders(Order order) {
        this.orders.add(order);
        order.setProduct(this);
    }
    
}
