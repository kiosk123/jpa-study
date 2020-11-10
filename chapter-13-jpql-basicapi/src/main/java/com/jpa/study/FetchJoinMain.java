package com.jpa.study;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Member;
import com.jpa.study.domain.MemberType;
import com.jpa.study.domain.Team;

/**
 * 페치 조인은 SQL 조인이 아니고 JPQL 성능 최적화를 위해 제공하는 기능
 * 연관된 엔티티나 컬렉션을 SQL 한번에 함께 조회하는 기능
 * join fetch 명령어 사용
 * 페치 조인 [left[outer] | inner] join fetch 조인경로
 */
public class FetchJoinMain {

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
            
            // Team에 속해있는 Member들 모두 조인하는 데 Member의 Team도 가져와서 설정함
            // fetch 조인시 on절 사용할 수 없음 where절 사용
            em.createQuery("select m from Member m inner join fetch m.team ")
              .getResultList();
            
            /**
             * 컬렉션 패치조인
             * DB입장에서 1대다 조인을 하면 같은 데이터를 여러개 출력된다 - 같은 결과값(같은 주소를 가지는)을 가지는 엔티티가 여러개 조회됨
             * distinct를 사용하자
             * JPQL distinct는 엔티티의 중복도 제거 (1차 캐시에 중복된게 있으면 제거)
             * SQL쿼리 distinct만으로 실제 중복제거는 안됨 그렇기 때문에 애플리케이션에서 중복제거를 시도하여
             * 같은 식별자를 가진 엔티티를 제거하게 된다
             */
            em.createQuery("select distinct t from Team t inner join fetch t.members ", Team.class)
              .getResultList()
              .forEach(team -> {
                  System.out.println("team = " + team.getName() + " | team members size : " + team.getMembers().size());
              });
            
            /**
             * 페치조인 대상 (여기서는 t.members)은 별칭(알리아스)를 줄 수 없다. - 하이버네이트에서는 가능하지만 쓰지말것
             * 잘못조작하면 위험함!!!
             * JPA에서의도한 것은 JPA fetch는 연관데이터를 다 가져오는 것
             * 
             * 예를 들어 Team에 속한 멤버중에 나이가 10살이상인 멤버 조인한다고
             * select distinct t from Team t inner join fetch t.members m and m.age > 10 이런식으로 사용금지!!!! 
             *============================================================================================
             * 둘이상의 컬렉션은 페치 조인 할 수 없다 !!!!!!!!!!!
             * 
             * 일대일, 다대일 같은 단일값 연관 필드들은 페치 조인해도 페이징이 가능하다.
             * 컬렉션을 페치조인하면 페이징 API(setFirstResult, setMaxResult) 사용할 수 없다.
             * 실제 SQL 결과를 통해 paging 처리가 되기 때문에 원하는 값이 안나올수 있음 (페이징은 철저하게 DB 중심적임)
             * 예를들어 TeamA에 5명의 멤버가 있으면 조인시 로우가 5라인이 생김
             * 그런상태에서 페이징 1개만 가져온다고 하면 TeamA에 멤버는 한명만 들어가는 예상치 못한 상황 발생
             * % 하이버네이트는 페이징시 메모리에 올라가고 나서 페이징 한다. 경고 로그를 남김 -> 메모리 다 쳐먹는다는 경고
             */
            em.createQuery("select distinct t from Team t inner join fetch t.members ", Team.class)
            .getResultList();
            
            em.createQuery("select m from Member m, Team t where m.team = t and m.age > 10 ", Member.class)
            .getResultList();
            
            /*
             * 페치 조인일때 컬렉션으로 인한 페이징 처리가 불가하기 때문에
             * 일대다 관계에서는 일반조인으로 데이터를 가져온 후 가져온 데이터의 컬렉션을 다시 조회하는데
             * 문제는 이때 lazy로딩이기 때문에 컬렉션 루프를 돌때마다 select 쿼리가 발생한다.
             *
             * 이런 것을 해결하기 위해
             * 페치 조인 대상 컬렉션에 @BatchSize(size = 값)을 설정한다. 
             * 값은 1000이하의 값을 준다
             * 
             */
            em.clear();
            //@BatchSize 애너테이션 설정 하거나 global 세팅 후 쿼리 동작 확인해볼것
            //TeamA와 TeamB와 연관된 member를 한꺼번에 다 가져온다
            em.createQuery("select t from Team t",Team.class)
            .setFirstResult(0)
            .setMaxResults(50)
            .getResultList()
            .get(0).getMembers().forEach(m -> System.out.println(m.getName()));
            

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
