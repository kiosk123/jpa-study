package com.jpa.study.domain;

import javax.persistence.Entity;
/**
 * 상속관계 매핑 - 싱글테이블 전략
 */
@Entity
public class Album extends Item {
    private String artist;
}
