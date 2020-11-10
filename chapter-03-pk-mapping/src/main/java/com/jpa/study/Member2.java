package com.jpa.study;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
/**
 * 테이블 전략으로 기본 키를 매핑한다. 
 * 사용할 테이블은 MY_SEQUENCES이며 데이터베이스에 생성해 둔다
 *
 * create table MY_SEQUENCES (
 *  sequence_name varchar(255) not null,
 *  next_val bigint,
 *  primary key (sequence_name)
 * )
 *
 */

@Entity
@TableGenerator(name = "MEMBER_SEQ_TABLE_GENERATOR", 
                table = "MY_SEQUENCES",
                pkColumnName = "sequence_name",
                pkColumnValue = "MEMBER_SEQ",
                allocationSize = 1) 
public class Member2 {
    @Id 
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_TABLE_GENERATOR")
    private Long id;
    
    @Column(name = "NAME", insertable = true, updatable = true, nullable = false)
    private String name;
    
    @Column(precision = 20, scale = 10) 
    private BigDecimal age;
    
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate; 
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    
    @Lob
    private String description;
    
    @Transient
    private int temp;
    
    public Member2() {}

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

    public BigDecimal getAge() {
        return age;
    }

    public void setAge(BigDecimal age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
