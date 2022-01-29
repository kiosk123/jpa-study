package com.jpa.study;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Member;
import com.jpa.study.domain.MemberType;
import com.jpa.study.domain.Team;

/**
 * 쿼리 한번으로 여러 테이블 로우 변경
 * 커밋 시점에 반영
 * 
 * executeUpdate()의 결과는 영향 받은 엔티티 수 반환
 * update, delete 지원
 * insert(insert into .. select, 하이버네이트 지원)
 * 
 * !!! 중요 !!!!
 * 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리 그렇기 때문에 이런현상을 방지하기 위해 다음과 같이 처리
 * - 벌크 연산을 먼저 실행
 * - 벌크 연산 수행 후 영속성 컨텍스트 초기화
 */
public class BulkOperationMain {

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
            
            /**
             * 벌크 연산 처리 후 영속성 컨텍스트 초기화 후
             * 조회 처리 할 것
             */
            
            //update 벌크 쿼리 실행시 insert가 반영됨(flush 자동 호출)
            int resultCount = em.createQuery("update Member m set m.name = 'older' where m.age > 30")
                                .executeUpdate();
            System.out.println("effected row : " + resultCount);
            em.clear(); // 벌크 연산 후 영속성 컨텍스트 초기화
            
            em.createQuery("select distinct m.name from Member m", String.class)
              .getResultList()
              .forEach(System.out::println);
            
            //delete
            resultCount = em.createQuery("delete from Member m where m.name = 'older'")
                            .executeUpdate();
            System.out.println("deleted row : " + resultCount);
            em.clear(); // 벌크연산 처리 후 영속성 컨텍스트 초기화 - 그리고 위에있는 객체(준영속) 재사용하지 말고 다시 조회해야함
            
            teamA = em.find(Team.class, teamA.getId()); // DB에 반영된 내용을 다시 가져옴
            teamA.getMembers().forEach(m -> System.out.println("================= " + m.getName()));
            
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
