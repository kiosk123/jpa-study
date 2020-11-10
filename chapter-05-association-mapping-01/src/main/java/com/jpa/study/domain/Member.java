package com.jpa.study.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Member {
    
    @Id @GeneratedValue
    private Long id;
    
    @Column(name = "USERNAME")
    private String name;
    
//    @Column(name = "TEAM_ID")
//    private Long teamId;
    
    @ManyToOne //하나의 팀에 여러 멤버가 소속되어 있으므로
    @JoinColumn(name = "TEAM_ID") //조인되는 컬럼이름
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
//        team.addMembers(this); //양방향 연관관계 처리를 한번에 하는 방법
    }
}
