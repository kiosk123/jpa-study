package com.jpa.study;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPAMain01Insert {

    public static void main(String[] args) {
        //하나의 EntityManagerFactory는 여러개의 EntityManager를 생성하는 역할
        //애플리케이션 로딩시점에 딱하나만 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        
        //트랜잭션당 하나씩 생성
        //EntityManager는 쓰레드간 공유 X
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 트랜잭션시작

        Member member = new Member();
        member.setId(1L);  //PK value
        member.setName("Hello");
        
        em.persist(member);
        tx.commit();
        
        em.close();
        emf.close();
    }

}
