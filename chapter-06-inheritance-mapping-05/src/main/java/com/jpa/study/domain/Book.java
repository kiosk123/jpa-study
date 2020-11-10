package com.jpa.study.domain;

import javax.persistence.Entity;

/**
 * 고급매핑 실전*
 */
@Entity
public class Book extends Item {
    private String author;
    private String isbn;
}
