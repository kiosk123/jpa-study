package com.jpa.study;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * DETACH
 */
public class JPAMain06Detach {
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        
        //트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜잭션시작
            Member member = em.find(Member.class, 1L);
            member.setName("Puppy");
            em.detach(member); //member는 준영속 상태이기 때문에 업데이트 반영안됨
            
            // em.clear(); // 영속성 컨텍스트를 통으로 날려(1차캐시) 조회한 엔티티를 준영속 상태가 됨
            // member = em.merge(member); // 준영속 상태를 다시 영속 상태로 변경 
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); //영속성 컨텍스트를 닫는 순간 준영속 상태가 됨
        }
        emf.close();

    }
}
