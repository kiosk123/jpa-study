package com.jpa.study;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Member;

public class PagingMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H2");

        // 트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // 트랜잭션시작
            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setName("member" + i);
                member.setAge(i);
                em.persist(member);
            }
            em.flush();
            em.clear();
            
            //페이징 쿼리
            List<Member> members= em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0) //시작 데이터 라인
                    .setMaxResults(10) //시작 데이터 라인 부터 데이터 10개 가져옴
                    .getResultList();

            members.forEach(m -> System.out.println(m.getName() +" : " + m.getAge()));
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
