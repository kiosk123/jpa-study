package com.jpa.study;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.jpa.study.domain.Address;
import com.jpa.study.domain.Member;

public class JPAMain {

    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H2");
        
        
        //트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜잭션시작
            Member member = new Member();
            member.setName("member1");
            member.setHomeAddress(new Address("homeCity", "street", "zipcode"));
            
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");
            
            member.getAddressHistoryAddresses().add(new Address("old1", "street", "zipcode"));
            member.getAddressHistoryAddresses().add(new Address("old2", "street", "zipcode"));
            em.persist(member);
            em.flush();
            em.clear();
            
            Member findMember = em.find(Member.class, member.getId());
            
            Address oldAddress = findMember.getHomeAddress();
            
            //값 타입은 새객체로 교체해주어야 한다.
            findMember.setHomeAddress(new Address("newcity", oldAddress.getStreet(), oldAddress.getZipcode()));
            
            //값 타입 컬렉션도 immutable 해야하기 때문에 다음과 같이 해야한다.
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");
            
            //equals를 잘 구현되어 있어야됨
            findMember.getAddressHistoryAddresses().remove(new Address("old1", "street", "zipcode"));
            findMember.getAddressHistoryAddresses().add(new Address("newCity1", "street", "zipcode"));
            
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

