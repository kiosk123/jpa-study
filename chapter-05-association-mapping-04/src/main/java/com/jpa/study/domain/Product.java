package com.jpa.study.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * 다대다 매핑 예제
 * 실제로 실무에서는 잘 사용하지 않는다(권장하지 않음)
 * 실제 비즈니스는 복잡하기 때문
 */
@Entity
public class Product {
    
    @Id @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String name;
    
    @ManyToMany(mappedBy = "products") //양방향 매핑
    private List<Member> members = new ArrayList<>();
    
    public Product() {}

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

    public List<Member> getMembers() {
        return members;
    }

    public void addMember(Member member) {
        this.members.add(member);
    }
    
}
