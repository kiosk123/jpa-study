package com.jpa.study.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Item {
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;
    
    private String name;
    private Integer price;
    private Integer stocktQuantity;
    
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
    
    public List<Category> getCategories() {
        return categories;
    }
    public void addCategory(Category category) {
        this.categories.add(category);
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
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Integer getStocktQuantity() {
        return stocktQuantity;
    }
    public void setStocktQuantity(Integer stocktQuantity) {
        this.stocktQuantity = stocktQuantity;
    }
}
