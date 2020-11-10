package com.jpa.study.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * 다대다 매핑 예제
 * 실제로 실무에서는 잘 사용하지 않는다(권장하지 않음)
 */
@Entity
public class Member {
    
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    
    private String userName;
    
    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT") //MEMBER_PRODUCT라는 테이블을 통해 조인됨
    private List<Product> products = new ArrayList<>();

    public Member() { }
    
    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        product.addMember(this);
        this.products.add(product);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
