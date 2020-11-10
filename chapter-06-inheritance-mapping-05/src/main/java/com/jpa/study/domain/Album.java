package com.jpa.study.domain;

import javax.persistence.Entity;

/**
 * 고급매핑 실전*
 */
@Entity
public class Album extends Item {
    private String artist;
    private String etc;
}
