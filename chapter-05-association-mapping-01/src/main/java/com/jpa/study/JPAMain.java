package com.jpa.study;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Member;
import com.jpa.study.domain.Team;

/**
 * !!!! 양방향 연관관계는 객체지향 단방향 설계가 다 끝나고 개발할때 필요할 시 그때가서 추가해도됨!!!!
 * !!!! 양방향 연관계시 toString(), lombok이나 JSON생성 라이브러리 사용시 무한루프 를 조심해야한다.!!! 중요 !!!!
 * 팁 : 1. lombok 사용시 toString()은 (왠만하면)빼고 사용하자
 *     2. 컨트롤러에서 Entity를 절대 반환하지 말 것 - Entity는 DTO로 변환하여 반환하는 것을 추천
 */
public class JPAMain {

    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H2");
        
        
        //트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜잭션시작
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);
            
            Member member = new Member();
            member.setName("USER");
//            member.setTeam(team);  //멤버와 팀 연관관계 맺어줌 - 객체지향의 연관관계를 맺어주는 방식
            
            //그리고 양방향 연관관계를 맺은 상태라면 team에도 Member를 세팅해줘야 한다. - 객체지향적 양방향 연관관계
//            team.getMembers().add(member);
            team.addMember(member); //둘중의 하나에서 양방향 연관관계를 한번에 처리하자
            
            em.persist(member);
//            em.flush(); //여기서 바로 객체 조회시 영속성 컨텍스트에서 가져오므로 insert 쿼리(DB반영) 보고싶으면 실행
            
//            Member findMember = em.find(Member.class, member.getId());
            Member findMember = em.find(Member.class, 13L);
            System.out.println("find Member's team : " + findMember.getTeam().getName());
            
            List<Member> members = findMember.getTeam().getMembers(); //양방향 매핑이 이뤄졌으므로 객체그래프 탐색함
            for (Member m : members) {
                System.out.println("member's name : " + m.getName() + ", id : " + m.getId());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

}

