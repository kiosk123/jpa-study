package com.jpa.study;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.jpa.study.domain.Member;

public class JoinQueryMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H2");

        // 트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // 트랜잭션시작
            Member member = new Member();
            member.setName("member1");
            member.setAge(18);
            em.persist(member);
            
            /**
             * INNER JOIN
             */
            TypedQuery<Member> query1 = em.createQuery("select m from Member m inner join m.team", Member.class);
            query1.getResultList();
            
            //조인대상 필터링 on 절 활용
            em.createQuery("select m, t from Member m inner join m.team t on t.name = 'A'").getResultList();
            
            
            /**
             * LEFT JOIN
             */
            query1 = em.createQuery("select m from Member m left join m.team", Member.class);
            query1.getResultList();
           
            /**
             * 세타 조인 (외래키로 참조되는 컬럼이 아닌 타입이 같은 컬럼을 가지고 조인하는 방식)
             */
            query1 = em.createQuery("select m from Member m, Team t where m.name = t.name", Member.class);
            query1.getResultList();
            
            /*
             * 연관관계 없는 엔티티 외부 조인 left, right
             */
            query1 = em.createQuery("select m from Member m left join Team t on m.name = t.name", Member.class);
            query1.getResultList();
            
            em.createQuery("select m, t from Member m right join Team t on m.name = t.name").getResultList();
            
            
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
