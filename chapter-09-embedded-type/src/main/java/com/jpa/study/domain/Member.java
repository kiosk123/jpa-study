package com.jpa.study.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {
    
    @Id @GeneratedValue
    private Long id;
    private String name;
    
    @Embedded //임베디드 사용 필드에 설정
    private Period  period;
    
    //같은 타입이 중복되어 사용될 경우 실제 테이블 매핑 컬럼과 어떻게 매칭될 껀지를 결정해야됨
    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "city",column = @Column(name = "HOME_CITY")),
            @AttributeOverride(name = "street",column = @Column(name = "HOME_STREET")),
            @AttributeOverride(name = "zipcode",column = @Column(name = "HOME_ZIPCODE"))})
    private Address homeAddress;
    
    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "city",column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street",column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode",column = @Column(name = "WORK_ZIPCODE"))})
    private Address workAddress;

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

    public Address getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
    }
    
}
