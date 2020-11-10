package com.jpa.study;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Member;
import com.jpa.study.domain.Team;

public class LazyInitailizationExceptionMain {

  public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H2");
        
        
        //트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜잭션시작
            Member member = insertNewMember(em, "TEAMC", "USER3");
            em.flush();
            em.clear(); //1차캐시 날림
            
          
            Member refMember = em.getReference(Member.class, member.getId());
            
            System.out.println("=====================");
            
            System.out.println("type : " + refMember.getClass().getName()); //하이버네이트 가짜 엔티티(프록시)조회
            System.out.println("is member's instance? : " + (refMember instanceof Member));
            System.out.println("id : " + refMember.getId());
            
            em.detach(refMember); //refMember 준영속
            //em.clear();
            
            //1차캐시에서 관리되지 않은 상태이면서 준영속 전에 name이 호출되지 않았고 준영속 이후에 호출할경우 LazyInitailizationException 발생
            System.out.println("name : " + refMember.getName()); 
            
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
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
