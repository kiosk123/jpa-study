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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
/*
 * @SequenceGenerator를 이용해서 테이블 마다 시퀀스를 생성(이미 생성된)한 후 생성된 시퀀스와 매핑할 수 있다
 */
//@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR", 
//                   sequenceName = "MEMBER_SEQ", // 매핑할 데이터 베이스 시퀀스 이름
//                   initialValue = 1,  //시작값, 기본값은 1
//                   
//                   //시퀀스 한번 호출에 증가하는 수(성능 최적화에 사용됨 데이터베이스  시퀀스 값이 하나씩 증가하도록 설정되어있으면 반드시 1로 설정), 기본값 50
//                   //50으로 설정시 미리 시퀀스 50을 생성해서 메모리에 올려놓고 메모리에 1씩 증가하게 된다, 반복적으로 DB에서 다음 시퀀스 호출 횟수를 줄여준다
//                   //동시성 이슈없이 다양한 문제가 해결
//                   allocationSize = 50
//                   )
public class Member {
    /**
     * @Id만 붙이면 직접할당 
     * @GeneratedValue : 사용시 타입은 Long쓰는 것을 권장!
     * 
     *      1. IDENTITY 기본키 생성을 디비(MySQL, PostgreSQL, SQL Sever, DB2)에 위임 (ex: mysql auto increment)
     *                  JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL을 실행하지만 
     *                  AUTO INCREMENT는 데이터베이스에 INSERT SQL을 실행한 후에 ID값을 알수 있기 때문에
     *                  이전략을 사용할 경우 em.persist() 시점에 즉시 INSERT SQL을 실행하고 DB에서 식별자를 조회한다
     *                  
     *      2. SEQUENCE 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트
     *                  오라클, PostegreSQL, DB2, H2등 데이터베이스에서 사용
     *                  시퀀스 오브젝트를 통해 값을 세팅 후 INSERT SQL 실행
     *                  
     *      3. TABLE    키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내
     *                  모든 데이터베이스에 적용가능 한 전력이지만 성능 이슈가 있음
     *                  운영에서는 잘 쓰지 않는 전략임
     */
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
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
