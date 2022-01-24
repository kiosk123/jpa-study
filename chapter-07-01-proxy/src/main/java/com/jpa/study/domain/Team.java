package com.jpa.study.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long id;
    
    private String name;
    
    /**
     * 다음과 같이 양방향 매핑이 가능하다.
     * 팀에 속한 멤버들을 확인하기 위한 매핑이다.
     * 대체적으로 객체 매핑은 단방향으로 하는 걸 권장한다.(양방향이면 신경쓸게 많음)
     * 
     * 다음은 Member클래스의 team 프로퍼티와 연관되어 매핑됨
     * 연관관계의 주인만이 외래 키를 관리(등록, 수정)할 수 있다.
     * 연관관계의 주인이 아닌쪽은 읽기만 가능!!! 중요!!!
     * 주인은 mappedBy 속성을 사용하지 않는다.
     * 주인이 아니면 mappedBy 속성으로 주인 지정을 한다
     * 주로 주인은 외래키가 있는 곳을 주인으로 정해야한다
     */
    @OneToMany(mappedBy = "team")
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