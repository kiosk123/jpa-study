package com.jpa.study;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Address;
import com.jpa.study.domain.Member;
import com.jpa.study.domain.Team;
import com.jpa.study.dto.UserDTO;

public class ProjectionMain {

    @SuppressWarnings({"unchecked", "unused"})
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H2");

        // 트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // 트랜잭션시작
            Member member = new Member();
            member.setName("member1");
            member.setAge(18);
            em.persist(member);
            em.flush();
            em.clear();
            
            //JPQL로 프로젝션된 엔티티는 1차캐시(영속성컨텍스트)에서 다 관리됨
            List<Member> members= em.createQuery("select m from Member m", Member.class).getResultList();
            
            //JPQL 작성시 최대한 SQL 작성과 비슷하게 할 것 - 어떻게 데이터를 가져오는지 예측을 쉽게 하기 위해
            List<Team> teams = em.createQuery("select t from Member m inner join m.team t", Team.class)
                                 .getResultList();
            
            //임베디드 타입 프로젝션 - 임베디드 타입 프로젝션은 단독으로는 안되고 반드시 엔티티를 통해서 조회
            List<Address> addresses = em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();
            
            //1. 스칼라 값 쿼리 - Object[]에 값 담기
            List<Object[]> scalas = em.createQuery("select m.name, m.age from Member m")
                              .getResultList();
            System.out.println("==================== scalas ============================");
            scalas.forEach(e -> {
                String str = "";
                for ( Object o : e) {
                    str += o;
                    str += " ";
                }
                str += "\n";
                System.out.println(str);
            });


            //2. 스칼라 값 쿼리 - DTO에 값 담기
            List<UserDTO> dtos = em.createQuery("select new com.jpa.study.dto.UserDTO(m.name, m.age) from Member m", UserDTO.class)
                                   .getResultList();
            
            // where 절에 between like 다 지원됨

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
