package com.jpa.study;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Entity가 붙은 클래스의 이름이 테이블 이름으로 치환되어 쿼리실행
 * @Entity(name = "MEMBER") 으로 할경우 JPQL 쿼리 대상이 MEMBER가 된다.
 * 
 * 클래스 이름과 테이블 이름이 다른경우
 * @Table(name = "테이블이름") 형식으로 매핑
 * @Table에서 DDL 생성시 사용되는 프로퍼티가 있다.
 * 이것을 이용해서 유니크 제약조건을 걸어줄수 있다.
 * @Table(uniqueConstraints = {@UniqueConstraint(name = "NAME_UNIQUE", 
 *                  columnNames = {"NAME, AGE"})}) 
 */

@Entity
//@Table(name = "MEMBER")
public class Member {
    
    /**
     * @ID PK설정된 필드에 설정
     * @Column 기본적으로는 필드이름으로 컬럼명과 매핑 
     *         필드이름과 컬럼명이 다른경우
     *         @Column(name = "컬럼명") 형식으로 매핑
     *         ex) 
     *         @Column(name = "USERNAME")
     *         private String name;
     *         
     * @Column 에서 DDL생성시에만 사용되는 프로퍼티가 있다.
     *         unique : 유니크 제약조건 여부
     *         length : 필드길이
     *         nullable : NULL값 가능 여부
     *         
     */
    @Id 
    private Long id;
    
    private String name;
    
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
    
}
