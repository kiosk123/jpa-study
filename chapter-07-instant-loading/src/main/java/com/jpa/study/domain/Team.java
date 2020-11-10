package com.jpa.study.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @ManyToOne, @OneToOne은 기본이 즉시로딩
 * @OneToMany @ManyToMany은 기본이 lazy 로딩
 * 
 * lazy 로딩으로 기본으로 설정할 것 즉시로딩은 비추천!!
 */
@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    
    private String name;
    
    //즉시 로딩
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private List<Member> members = new ArrayList<>(); //Null 포인터 방지
    
    public Team() {}
    
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
    public List<Member> getMembers() {
        return members;
    }
    public void addMember(Member member) {
        this.members.add(member); //한번에 연관관계 처리
        member.setTeam(this);
    }
}