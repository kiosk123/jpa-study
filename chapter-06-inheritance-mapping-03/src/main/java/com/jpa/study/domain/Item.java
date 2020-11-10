package com.jpa.study.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * 상속관계 매핑 - 구현클래스마다 테이블 전략
 * !!!! 중요 이 전략을 사용할 때의 부모 클래스는 abstract 클래스로 설정되어야함
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item {
    
    @Id @GeneratedValue
    private Long id;
    
    private String name;
    private int price;
    
}
