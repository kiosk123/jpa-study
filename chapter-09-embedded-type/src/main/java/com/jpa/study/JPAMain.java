package com.jpa.study;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Address;
import com.jpa.study.domain.Member;
import com.jpa.study.domain.Period;

public class JPAMain {

    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H2");
        
        
        //트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜잭션시작
            Member member = new Member();
            member.setName("HELLO");
            member.setHomeAddress(new Address("seoul", "AAA", "12312"));
            member.setWorkAddress(new Address("daejeon", "BBB", "12312"));
            member.setPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));
            
            em.persist(member);
      
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

