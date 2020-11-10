package com.jpa.study;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Child;
import com.jpa.study.domain.Parent;

/*
 * 영속성 전이 테스트
 */
public class JPAMain {

    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H2");
        
        
        //트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜잭션시작
            
            Child child1 = new Child();
            child1.setName("child1");
            
            Child child2 = new Child();
            child2.setName("child2");
            
            Parent parent = new Parent();
            //Parent children에 Child를 등록하고 persist는 Parent만 등록하면 Child도 persist된다.
            //왜냐하면 Parent의 children에 영속성 전이(cascade) 설정을 했기 때문이다.
            parent.addChild(child1);
            parent.addChild(child2);
            em.persist(parent);
            em.flush();
            em.clear();
            
            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildren().remove(0); //DELETE (쿼리 실행 orphanRemoval = true 설정된경우)
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

