package com.jpa.study;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Member;
import com.jpa.study.domain.MemberType;

public class TypeExpresstion {

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
                member.setMemberType(MemberType.values()[i % 3]);
                member.setAge(i);
                em.persist(member);
            }
            em.flush();
            em.clear();
            
            /*
             * 문자 표현
             * */
            List<Object[]> items = em.createQuery("select m, 'Hello', 'She\"s' from Member m").getResultList();
            System.out.println(items.get(0)[2]); //She"s 출력
            
            /*
             * 숫자 표현 L(long) D(double) F(float)
             * */
            items = em.createQuery("select m, 10L, 10.5D, 10.2F from Member m").getResultList();
            System.out.println(items.get(0)[2]); //10.5
            
            /*
             * enum 표현 : enum 값 비교시 패키지 명을 포함한 전체경로를 설정
             * */
            em.createQuery("select m from Member m where m.memberType = com.jpa.study.domain.MemberType.ADMIN", Member.class).getResultList();
            
            /*
             * enum 비교시 파라미터를 이용하여 설정하는 것을 추천
             * */
            em.createQuery("select m from Member m where m.memberType = :type", Member.class)
                .setParameter("type", MemberType.USER)
                .getResultList();
            
            /**
             * type을 이용해서 비교를 할 수 있다.(다형성을 활용하고 싶을때 즉 엔티티가 상속관계로 DB와 매핑되어있을 때)
             * 즉 @DiscriminatorValue, @DiscriminatorColumn 설정되어 있는 클래스간 사용됨 
             * Item은 Book의 슈퍼클래스
             * "select i from Item i where type(i) = Book
             */
         
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
