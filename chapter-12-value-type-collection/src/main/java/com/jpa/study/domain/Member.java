package com.jpa.study.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Member {
    
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;
    
    @Embedded 
    private Period  period;
    
    @Embedded
    private Address homeAddress;
    
    // ElementCollection - 기본은 지연로딩
    @ElementCollection 
    @CollectionTable(name = "FAVORITE_FOOD", 
                     joinColumns = {@JoinColumn(name = "MEMBER_ID")})
    @Column(name = "FOOD_NAME") //예외적으로 FAVORITE_FOOD에 FOOD_NAME 컬럼 생성
    private Set<String> favoriteFoods = new HashSet<>();
    
    // ElementCollection - 기본은 지연로딩
    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns = {@JoinColumn(name = "MEMBER_ID")})
    private List<Address> addressHistoryAddresses = new ArrayList<>();
    
    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public List<Address> getAddressHistoryAddresses() {
        return addressHistoryAddresses;
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

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
}
