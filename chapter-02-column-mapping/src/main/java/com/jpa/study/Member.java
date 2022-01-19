package com.jpa.study;



import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
public class Member {
   
    @Id 
    private Long id;
    
    /* *
     * insertable, updatable : JPA 상에서 데이터 삽입과 변경 가능여부 , 
     * nullable : null 허용여부 (DDL, DML(하이버네이트) 반영)
     * length : 문자 길이 제약조건 (DDL)
     * unique 제약 조건 걸기 -> 잘 쓰지 않음 하이버네이트가 제약조건 명을 랜덤으로 생상하기 때문
     * 일반적으로 클래스에 설정하는 @Table을 활용헤서 unique 제약조건 설정
     * ex) @Table(uniqueConstraints = {@UniqueConstraint(name = "NAME_UNIQUE", 
     *                 columnNames = {"NAME, AGE"})}) 
     * columnDefinition 디비 컬럼 설정 정보 직접 설정(특정 디비에 종속적인 옵션, DDL) 
     * ex) columnDefinition = "varchar(100) default 'EMPTY'"
     * */
    @Column(name = "NAME", insertable = true, updatable = true, nullable = false)
    private String name;
 
    /**
     * BigDecimal과 같은 큰 숫자타입의 경우에
     * precision : 소수점 포함 전체 숫자 길이 설정 (DDL)
     * scale : 소수의 자리수 설정 (DDL)
     * 다만 double, float에 적용되지 않는다.
     * 정밀한 소수를 다루어야 할때만 사용
     */
    @Column(precision = 20, scale = 10) 
    private BigDecimal age;
    
    /**
     * EnumType.ORDINAL : enum이 순서를 데이터베이스에 저장 (기본값) - 쓰지말 것 
     * EnumType.STRING : enum의 이룸을 데이터베이스에 저장
     */
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate; //하이버네이트 최신 버전은 LocalDate, LocalDateTime 사용가능
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    
    @Lob
    private String description;
    
    @Transient //엔티티 매핑대상에서 제외되는 필드에 사용
    private int temp;
    
    public Member() {}

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
