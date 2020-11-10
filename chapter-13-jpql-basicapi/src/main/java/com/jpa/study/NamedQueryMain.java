package com.jpa.study;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Member;
import com.jpa.study.domain.MemberType;
import com.jpa.study.domain.Team;

public class NamedQueryMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H2");

        // 트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // 트랜잭션시작
            Team teamA = new Team();
            teamA.setName("TeamA");
            em.persist(teamA);
            
            Team teamB = new Team();
            teamB.setName("TeamB");
            em.persist(teamB);

            
            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setName("member" + i);
                member.setMemberType(MemberType.values()[i % 3]);
                member.setAge(i);
                if (i % 2 == 0) {
                    member.setTeam(teamA);
                }
                else {
                    member.setTeam(teamB);
                }
                em.persist(member);
            }
            em.flush();
            em.clear();
            
            //@NameQuery에 정의한 쿼리 사용
            em.createNamedQuery("Member.findByUsername", Member.class)
              .setParameter("name", "member0")
              .getResultList()
              .forEach(m -> System.out.println(m.getName()));
            
            //META-INF 결로에 설정된 xml 파일에 있는 NamedQuery실행
            em.createNamedQuery("Member.count", Long.class)
              .getSingleResult();
            
            em.createNamedQuery("Team.findByName", Team.class)
              .setParameter("name", "TeamA")
              .getResultList()
              .forEach(t -> {System.out.println(t.getName());});
            
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

}
