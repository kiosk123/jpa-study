package com.jpa.study.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * 상속관계 매핑 - 싱글테이블 전략(기본값)
 */
@Entity
/*
 *  @DiscriminatorColumn  
 *  기본적으로 DTYPE이라는 컴럼을 (생성)을 통해 어떤 타입의 테이터인지 구분용으로 사용할 수 있다. 
 *  싱글 테이블 전략에서는 필수!!!
 */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn 
public class Item {
    
    @Id @GeneratedValue
    private Long id;
    
    private String name;
    private int price;
    
}
