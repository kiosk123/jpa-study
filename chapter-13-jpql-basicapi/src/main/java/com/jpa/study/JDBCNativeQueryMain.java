package com.jpa.study;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Session;
import com.jpa.study.domain.Member;

/*
 * 네이티브 쿼리 실행시(JDBC, JDBCTemplate 이용)
 * 1차캐시(영속성 컨텍스트) 모두 플러시하고 실행할 것
 */
public class JDBCNativeQueryMain {

    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H2");
        
        
        //트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin(); // 트랜잭션시작
            Member member = new Member();
            member.setName("member1");
            em.persist(member);
            em.flush();
            em.clear();
            
            //네이티브 쿼리 시 1차캐시 먼저 플러시처리 후 네이티브 쿼리 실행
            Session session = em.unwrap(Session.class);
            session.doWork(conn -> {
                // TODO 코딩
                //conn.prepareStatement("select * from MEMBER");
                
            });
            
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

