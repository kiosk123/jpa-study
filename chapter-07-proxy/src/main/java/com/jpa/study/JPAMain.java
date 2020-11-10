package com.jpa.study;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Member;
import com.jpa.study.domain.Team;

public class JPAMain {

    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H2");
        
        
        //트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜잭션시작
            Member member = insertNewMember(em, "TEAMB", "USER2");
//            Member findMember = em.find(Member.class, 2L);
            em.flush();
            em.clear(); //1차캐시 날림
            
            //getReference DB에서 조회하는 것이 아닌 1차적으로 프록시 객체 호출(1차캐시에 없을경우 1차 캐시에 있다면 실제 엔티티를 반환)
            //타입 체크시 주의 == 대신에 instanceof를 사용!!!
            Member findMember = em.getReference(Member.class, member.getId());
            
            System.out.println("=====================");
            
            System.out.println("type : " + findMember.getClass().getName()); //하이버네이트 가짜 엔티티(프록시)조회
            System.out.println("is member's instance? : " + (findMember instanceof Member));
            System.out.println("id : " + findMember.getId());
            System.out.println("name : " + findMember.getName()); //호출하는 시점에 이름 가져옴
            
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
    
    private static Member insertNewMember(EntityManager em, String teamName, String userName) {
        Team team = new Team();
        team.setName(teamName);
        em.persist(team);
        
        Member member = new Member();
        member.setName(teamName);
        member.setTeam(team);
        em.persist(member);
        return member;
    }

}

