package com.jpa.study;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPQLMain01 {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        List<Member> members = new ArrayList<Member>();
        
        //트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        // em.setFlushMode(FlushModeType.AUTO);   // 커밋이나 쿼리를 실행할때 플러시 기본값
        // em.setFlushMode(FlushModeType.COMMIT); // 커밋시 플러시
        try {
            tx.begin();
            //JPQL 대상은 테이블이 아닌 테이블과 매핑된 클래스임, JPQL은 기본적으로 내부적으로 무조건 flush()를 호출 후 쿼리실행
            members = em.createQuery("select m from Member as m", Member.class)
                        .setFirstResult(5) // 조회된 데이터의 6번째 라인부터
                        .setMaxResults(8)  // 최대 8개까지 데이터를 조회한다.
                        .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
