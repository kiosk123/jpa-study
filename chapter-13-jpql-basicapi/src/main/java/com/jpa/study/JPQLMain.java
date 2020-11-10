package com.jpa.study;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.jpa.study.domain.Member;

public class JPQLMain {

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
             * 기본 쿼리
             */
            //반환 타입정보가 명확할때 
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.name from Member m", String.class);
            TypedQuery<Member> query3 = em.createQuery("select m from Member m where m.id =:id", Member.class);
            query3.setParameter("id", member.getId()); //파라미터 반인딩
            
            /**
             * 반환값이 여려개 일때, 없으면 빈 리스트 반환
             */
            List<Member> members = query1.getResultList(); 
            
            /**
             *  반환값이 하나일때, 
             *  결과가 없으면 NoResultException 발생
             *  결과가 둘 이상이면 NonUniqueResultException 발생
             *  Spring Data JPA에서는 Null 또는 Optional로 반환
             */
            Member singleMember =  query3.getSingleResult();
            
            
            //반환 타입정보가 명확하지 않을때
            Query q1 = em.createQuery("select m.name, m.age from Member m");
            

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
