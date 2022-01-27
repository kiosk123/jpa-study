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
            
            /**
             * 1. 같은 객체를 사용할꺼면 clone을 이용해서 각각의 객체에 세팅할 것
             * 2. 같은 객체를 참조하는 경우 중간에 값을 못바꾸게 set메서드 다 제거하거나 접근제한자 private으로 변경 //추천
             * 3. 정말로 수정이 필요한 경우 같은 값을 가지는 각각의 객체를 생성해서 설정한다.
             * 왜냐하면 같은 객체를 참조시 값이 바뀌는 경우 모두다 영향을 줄수 있음 
             * 공유참조로 인한 부작용을 피하자!
             */
            Address address = new Address("seoul", "AAA", "12312");
            Member member = new Member();
            member.setName("HELLO");
            
            member.setHomeAddress(address);
            member.setWorkAddress(address.clone()); //clone을 이용한 값 설정
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

