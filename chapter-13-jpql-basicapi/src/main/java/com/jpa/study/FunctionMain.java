package com.jpa.study;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Member;
import com.jpa.study.domain.MemberType;
import com.jpa.study.domain.Team;

public class FunctionMain {

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
             * concat - 문자열 결합
             */
            List<String> item = em.createQuery("select concat(m.name, ' Hello!') from Member m", String.class).getResultList();
            
            /**
             * substring - 문자열 자르기
             * substring(target, 시작번호, 잘라낼 갯수)
             */
            item = em.createQuery("select substring(m.name, 1, 6) from Member m", String.class).getResultList();
            
         
            /**
             * trim - 공백제거
             */
            item = em.createQuery("select trim(m.name) from Member m", String.class).getResultList();
            
            /**
             * length - 길이 출력
             */
            List<Integer> lens = em.createQuery("select length(m.name) from Member m", Integer.class).getResultList();
            
            /**
             * lower - 문자를 전부 소문자로 변경
             */
            item = em.createQuery("select lower(m.name) from Member m", String.class).getResultList();
            
            /**
             * upper - 문자를 전부 대문자로 변경
             */
            item = em.createQuery("select upper(m.name) from Member m", String.class).getResultList();
            
            /**
             * location - 지정된 문자의 위치를 반환, 없으면 0반환
             */
            List<Integer> positions = em.createQuery("select locate('mem', m.name) from Member m", Integer.class).getResultList();
            
            /**
             * abs - 절대 값 반환
             */
            Integer abs = em.createQuery("select abs(-1) from Member m", Integer.class)
                            .setFirstResult(0)
                            .setMaxResults(1)
                            .getSingleResult();
            System.out.println("-1's abs is : " + abs);
            
            /**
             * sqrt - root 값을 구함
             */
            Double sqrt = em.createQuery("select sqrt(2) from Member m", Double.class)
                            .setFirstResult(0)
                            .setMaxResults(1)
                            .getSingleResult();
            System.out.println("2's sqrt is : " + sqrt);
            
            /**
             * mod - 나머지 값을 구함
             */
            Integer remain = em.createQuery("select mod(2, 5) from Member m", Integer.class)
                                .setFirstResult(0)
                                .setMaxResults(1)
                                .getSingleResult();
            System.out.println("2 mod 5 is " + remain);
            
            /**
             * size - @OneToMany 설정되어 있는 컬렉션의 size를 구한다.
             */
            Integer size = em.createQuery("select size(t.members) from Team t where t.name = 'TeamA'", Integer.class).getSingleResult();
            System.out.println("The members' size of TeamA is " + size);
            
            //Team을 참조하고 있는 전체 member 수를 카운트
            size = em.createQuery("select size(t.members) from Team t", Integer.class).getSingleResult();
            System.out.println("All member' size referenced from Team is " + size);
            
            /**
             * 사용자 정의 함수
             * 하이버네이트는 사용전 방언에 추가해야함
             * 사용하는 DB방언을 상속받고, 사용자 정의 함수를 등록한다.
             * 1. dialect를 하나 생성한다.
             * 2. DB방언을 상속받아 구현하고 구현체를 persistenc.xml에 등록한다
             * 3. function 키워드를 이용하여 등록한 사용자 정의 함수를 사용한다.
             */
            String gc = em.createQuery("select function('group_concat', m.name) from Member m", String.class).getSingleResult();
            System.out.println(gc);// member0,member1,member2,member3,member4,member5,member6,...
            
            //하이버네이트에서는 다음과 같은 방법으로 사용하는 것도 가능
            gc = em.createQuery("select group_concat(m.name) from Member m", String.class).getSingleResult();
            
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
