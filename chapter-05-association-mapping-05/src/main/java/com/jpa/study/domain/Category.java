package com.jpa.study.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Category {
    
    @Id @GeneratedValue
    @Column(name = "GATEGORY_ID")
    private Long id;
    
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent; //상위 카테고리 셀프 조인(자기자신의 PK를 참조하는 외래키)
    
    
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<Category>();

    //CATEGORY_ITEM을 통해서 ITEM과 다대다 조인
    @ManyToMany
    //joinColumns는 CATEGORY 테이블이 CATEGORY_ITEM 테이블의 CATEGORY_ID와 조인
    //inverseJoinColumns ITEM 테이블이 CATEGORY_ITEM 테이블의 ITEM_ID와 조인
    @JoinTable(name = "CATEGORY_ITEM", 
               joinColumns = @JoinColumn(name = "CATEGORY_ID"), 
               inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    private List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        item.addCategory(this);
        this.items.add(item);
    }

    public List<Category> getChild() {
        return child;
    }

    public void addChild(Category child) {
        child.setParent(this);
        this.child.add(child);
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

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }
}
