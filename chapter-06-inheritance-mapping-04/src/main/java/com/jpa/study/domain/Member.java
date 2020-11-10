package com.jpa.study.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * DB상 상속관계는 아니지만
 * 객체상에서 필드가 반복될 경우
 * 반복되는 필드만 상위클래스로 빼는 방법
 *
 */
@Entity
public class Member extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private Long name;
}
