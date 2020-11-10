package com.jpa.study;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Member;
import com.jpa.study.domain.MemberType;
import com.jpa.study.domain.Team;

/**
 * 경로 표현식이란 .을 찍어 객체 그래프를 탐색
 * select m.username -> 상태필드(단순 값 저장) - 경로 탐색의 끝, 더이상 탐색을 할 수 없다.
 * from Member m
 * join m.team t -> 단일 값 연관 필드 - 묵시적 내부조인 발생, 탐색 가능하다
 * join m.orders o -> 컬렉션 값 연관필드 - 묵시적 내부 조인 발생, 탐색 불가지만 From 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색가능하다.
 * where t.name = '팀A'
 *
 * !!!!!!!!!!!!!!!!! 묵시적 조인이 발생하도록 쿼리를 작성하지 말 것 !!!!!!!!!!!!!!!!!!!!!!!!ㅒ
 */
public class PathExpression {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H2");

        // 트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // 트랜잭션시작
            Team teamA = new Team();
            teamA.setName("TeamA");
            em.persist(teamA);
            
            Team teamB = new Team();
            teamB.setName("TeamB");
            em.persist(teamB);

            
            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setName("member" + i);
                member.setMemberType(MemberType.values()[i % 3]);
                member.setAge(i);
                if (i % 2 == 0) {
                    member.setTeam(teamA);
                }
                else {
                    member.setTeam(teamB);
                }
                em.persist(member);
            }
            em.flush();
            em.clear();
            
            // 상태 필드 예
            em.createQuery("select m.name from Member m", String.class)
              .getResultList();
            
            // 단일 값 연관 필드 -> 상태 필드 탐색 - 묵시적 내부 조인 발생(묵시적 내부조인이 발생하도록 쿼리를 작성하면 안됨!!!!!!!!!)
            em.createQuery("select m.team.name from Member m", String.class)
              .getResultList();
            
            // 컬랙션값 연관 경로 -> 탐색x(왜냐하면 컬렉션이라서...)
            List members = em.createQuery("select t.members from Team t").getResultList();
            
            // Team에 소속되어 있는 member를 모두 탐색 - 이렇게 잘 사용하지 않음
            members.forEach(e -> {
                Member member = (Member)e;
                System.out.println(member.getName());
            });
            
            //컬렉션의 size 함수 호출
            Integer size = (Integer)em.createQuery("select t.members.size from Team t").getSingleResult();
            System.out.println("size : " + size);
            
            //보통 이렇게 사용
            em.createQuery("select m.name from Team t join t.members m", String.class)
              .getResultList()
              .forEach(System.out::println);
            
            //이렇게도 가능
            em.createQuery("select m.name from Team t, Member m where m.team = t", String.class)
                .getResultList()
                .forEach(System.out::println);;
            
          
            
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
