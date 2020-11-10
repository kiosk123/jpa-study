package com.jpa.study.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @ManyToOne, @OneToOne은 기본이 즉시로딩
 * @OneToMany @ManyToMany은 기본이 lazy 로딩
 * 
 * lazy 로딩으로 기본으로 설정할 것 즉시로딩은 비추천!!
 */
@Entity
public class Member {
    
    @Id @GeneratedValue
    private Long id;
    
    @Column(name = "USERNAME")
    private String name;
    
    //즉시로딩
    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "TEAM_ID") 
    private Team team;
    
    public Member() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
