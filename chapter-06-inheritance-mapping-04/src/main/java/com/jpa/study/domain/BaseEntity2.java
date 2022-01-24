package com.jpa.study.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity2 {
    
    @Id @GeneratedValue
    private Long id;
    private String name;
}
