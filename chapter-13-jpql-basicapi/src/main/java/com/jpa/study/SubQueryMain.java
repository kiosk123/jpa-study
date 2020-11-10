package com.jpa.study;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Member;

/**
 * JPA 서브 쿼리 한계
 * JPA는 where, having절에서만 서브쿼리 사용 가능 (하이버네이트는 select 절도 가능 -> select (select ) from ...)
 * from 절의 서브 쿼리는 현재 JPQL에서 불가능 -> 조인으로 풀 수 있으면 풀어서 해결
 */
public class SubQueryMain {

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
            
            /**
             * 나이가 평균보다 많은 회원
             */
            em.createQuery("select m from Member m where m.age > (select avg(m2.age) from Member m2)", Member.class)
              .getResultList();
            
            /**
             * 한 건이라도 주문한 고객
             */
            em.createQuery("select m from Member m where (select count(o) from Order o where m = o.member) > 0", Member.class)
              .getResultList();
            
            /**
             * 서브쿼리 지원함수
             * [NOT] EXISTS (subquery): 서브쿼리에 결과가 존재하면 참
             * {ALL | ANY | SOME} (subquery)
             * ALL 모두 만족하면 참
             * ANY, COME: 같은 의미 조건을 하나라도 만족하면 참
             * [NOT] IN (subquery): 서브쿼리의 결과중 하나라도 같은 것이 있으면 참
             * 참고 : https://gent.tistory.com/287
             */
             //팀 A 소속인 회원
            em.createQuery("select m from Member m where exists (select t from m.team t where t.name = 'A')").getResultList();
            
            //전체 상품 각각의 재고보다 주문량이 많은 주문들
            em.createQuery("select o from Order o where o.orderAmount > all (select p.stockAmount from Product p)").getResultList();
            
            //어떤 팀이든 팀에 소속된 회원
            em.createQuery("select m from Member m where m.team = any (select t from Team t)").getResultList();
            
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
