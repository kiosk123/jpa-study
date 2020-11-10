package com.jpa.study;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Member;
import com.jpa.study.domain.MemberType;
import com.jpa.study.domain.Team;

public class EntitiyDirectUseMain {

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
            
            /**
             * 둘다 같은 결과
             */
            em.createQuery("select t from Team t where t = :team", Team.class)
              .setParameter("team", teamA)
              .getResultList();
            
            em.createQuery("select t from Team t where t.id = :teamId", Team.class)
            .setParameter("teamId", teamA.getId())
            .getResultList();
            
            //외래키 비교
            em.createQuery("select m from Member m where m.team = :team", Member.class)
            .setParameter("team", teamA)
            .getResultList();
            
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
