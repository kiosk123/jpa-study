package com.jpa.study.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Team extends BaseEntity {
    
    @Id @GeneratedValue
    private Long id;
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
