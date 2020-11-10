package com.jpa.study.domain;

import javax.persistence.Entity;

/**
 * 고급매핑 실전*
 */
@Entity
public class Movie extends Item {
    private String director;
    private String actor;
}
