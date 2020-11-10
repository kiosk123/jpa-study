package com.jpa.study.domain;

import javax.persistence.Entity;
/**
 * 상속관계 매핑 - 조인전략
 *
 */
@Entity
//@DiscriminatorValue("A") //@DiscriminatorValue를 안붙이면 기본은 엔티티명
public class Album extends Item {
    private String artist;
}
