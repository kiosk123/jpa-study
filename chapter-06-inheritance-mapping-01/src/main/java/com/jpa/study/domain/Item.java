package com.jpa.study.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * 상속관계 매핑 - 조인전략
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn //기본적으로 DTYPE이라는 컴럼을 (생성)을 통해 어떤 타입의 테이터인지 구분용으로 사용할 수 있다.
public class Item {
    
    @Id @GeneratedValue
    private Long id;
    
    private String name;
    private int price;
    
}
