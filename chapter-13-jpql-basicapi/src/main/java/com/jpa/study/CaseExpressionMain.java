package com.jpa.study;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Member;
import com.jpa.study.domain.MemberType;

public class CaseExpressionMain {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H2");

        // 트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // 트랜잭션시작
            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                if (i % 3 == 0) {
                    member.setName("member" + i);
                }
                else if (i % 5 == 0) {
                    member.setName("관리자");
                }
                member.setMemberType(MemberType.values()[i % 3]);
                member.setAge(i);
                em.persist(member);
            }
            em.flush();
            em.clear();
            
            //기본 case 식 - 컨디션에 따른 조건을 넣을 수 있다
            List<Object> values = em.createQuery(
                      "select "
                    + "case when m.age <= 10 then '학생요금'"
                    +     " when m.age >= 60 then '경로요금'"
                    +     " else '일반요금' "
                    + "end "
                    + "from Member m").getResultList();
            values.forEach(System.out::println);
         
            //단순 case 식 - 단수 값 비교에 따른 케이스식
            values = em.createQuery(
                    "select "
                  + "case t.name when 'A' then '1st'"
                  +            " when 'B' then '2nd'"
                  +            " else '3rd' "
                  + "end "
                  + "from Team t").getResultList();
            
            //coalesce : 하나씩 조회해서 null이 아니면 반환
            //사용자 이름이 없으면 '이름 없는 회원'을 반환
            values = em.createQuery("select coalesce(m.name, '이름 없는 회원') from Member m").getResultList();
            values.forEach(System.out::println);
            
            //nullif : 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
            //사용자 이름이 관리자면 null을 반환하고 나머지는 본인의 이름을 반환
            values = em.createQuery("select nullif(m.name, '관리자') from Member m").getResultList();
            values.forEach(System.out::println);
            
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
