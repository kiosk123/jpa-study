# 1. 프로젝트 생성과 기본적인 CRUD(JPQL포함)와 플러시, 준영속, 에러처리 

## 테스트를 위한 DB 생성
```sql
create table MEMBER (
   id bigint not null,
   name varchar(255),
   constraint MEMBER_PK primary key(id)
)
```

## persistence.xml 설정
JPA는 MAVEM 스타일의 프로젝트 경로 기준  
**src/main/resources/META/persistence.xml** 파일을 생성해야 한다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2" 
    xmlns="http://xmlns.jcp.org/xml/ns/persistence"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">

	<!-- 
		JPA 디비 커넥션 설정 이나 JPA 설정이 다른 경우 persitence unit을 여러개 만들어서 설정한다.
		Persistence.createEntityManagerFactory("hello"); 형태로 호출
	 -->
	<persistence-unit name="hello">
		<properties>
			<property name="javax.persistence.jdbc.driver" value="org.h2.Driver" />
			<property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test" />
			<property name="javax.persistence.jdbc.user" value="sa" />
			<property name="javax.persistence.jdbc.password" value="" />
			
			<!-- 하이버네이트 방언 설정 -->
			<property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect" />
			
			<property name="hibernate.show_sql" value="true" />
			<property name="hibernate.format_sql" value="true"/>
			<property name="hibernate.use_sql_comments" value="true"/>
			
			<!-- 하이버네이트가 한번에 처리할 쿼리 갯수 -->
			<!-- <property name="hibernate.jdbc.batch_size" value="10"/> -->
			
			<!-- 
				하이버네이트가 데이터베이스 스키마를 컨트롤함
			    
				create - 애플리케이션 실행마다 기존 테이블 다 제거 후 테이블 생성
			    create-drop - create와 같으나 애플리케이션 종료시점에 테이블 DROP
			    update - 기존 테이블과 데이터를 삭제하지 않고 테이블의 변경(추가된)부분만 반영 - (컬럼추가, 데이터추가 등...)
			    validate - 엔티티와 테이블이 정상 매핑되었는지만 확인
			    none - 사용하지 않음(기본값)
			    
			    * 주의사항 - 개인 개발에서는 써도 되는데 여러명이 쓰는 테스트나 운영서버는 되도록 직접 SQL로 처리
			    1. 개발 초기 단계는 create 또는 update
			    2. 테스트 서버는 update 또는 validate
			    3. 스테이징과 운영 서버는 validate 또는 none
			 -->
			<property name="hibernate.hbm2ddl.auto" value="create" /> 
		</properties>

	</persistence-unit>
</persistence>
```

### MEMBER 테이블과 매핑한 MEMBER 엔티티 
```java
/**
 * @Entity가 붙은 클래스의 이름이 테이블 이름으로 치환되어 쿼리실행
 * @Entity(name = "MEMBER") 으로 할경우 JPQL 쿼리 대상이 MEMBER가 된다.
 * 
 * 클래스 이름과 테이블 이름이 다른경우
 * @Table(name = "테이블이름") 형식으로 매핑
 * @Table에서 DDL 생성시 사용되는 프로퍼티가 있다.
 * 이것을 이용해서 유니크 제약조건을 걸어줄수 있다.
 * @Table(uniqueConstraints = {@UniqueConstraint(name = "NAME_UNIQUE", 
 *                  columnNames = {"NAME, AGE"})}) 
 */

@Entity
//@Table(name = "MEMBER")
public class Member {
    
    /**
     * @ID PK설정된 필드에 설정
     * @Column 기본적으로는 필드이름으로 컬럼명과 매핑 
     *         필드이름과 컬럼명이 다른경우
     *         @Column(name = "컬럼명") 형식으로 매핑
     *         ex) 
     *         @Column(name = "USERNAME")
     *         private String name;
     *         
     * @Column 에서 DDL생성시에만 사용되는 프로퍼티가 있다.
     *         unique : 유니크 제약조건 여부
     *         length : 필드길이
     *         nullable : NULL값 가능 여부
     *         
     */
    @Id 
    private Long id;
    
    private String name;
    
    public Member() {}
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
```

## 예제코드 위치
src/main/java/com/jpa/study  
- Insert
```java
public class Insert {

    public static void main(String[] args) {
        //하나의 EntityManagerFactory는 여러개의 EntityManager를 생성하는 역할
        //애플리케이션 로딩시점에 딱하나만 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        
        //트랜잭션당 하나씩 생성
        //EntityManager는 쓰레드간 공유 X
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 트랜잭션시작

        Member member = new Member();
        member.setId(1L);  //PK value
        member.setName("Hello");
        
        em.persist(member);
        tx.commit();
        
        em.close();
        emf.close();
    }

}
```
- Delete
```java
public class Delete {

    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        
        //트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜잭션시작
            Member member = em.find(Member.class, 1L);
            em.remove(member);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

}
```
- Modify (Update)
```java
public class Modify {

    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        
        //트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜잭션시작
            Member member = em.find(Member.class, 1L);
            member.setName("Puppy");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

}
```
- Flush
```java
public class Flush {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜잭션시작
            Member member = em.find(Member.class, 1L);
            member.setName("Hello");

            // commit() 호출시 디비에 쿼리가 반영되는 것이 아닌 바로 디비에 반영됨
            em.flush();
     
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
```
- JPQL
```java
public class JPQL {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        List<Member> members = new ArrayList<Member>();
        
        //트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        // em.setFlushMode(FlushModeType.AUTO);   // 커밋이나 쿼리를 실행할때 플러시 기본값
        // em.setFlushMode(FlushModeType.COMMIT); // 커밋시 플러시
        try {
            tx.begin();
            //JPQL 대상은 테이블이 아닌 테이블과 매핑된 클래스임, JPQL은 기본적으로 내부적으로 무조건 flush()를 호출 후 쿼리실행
            members = em.createQuery("select m from Member as m", Member.class)
                        .setFirstResult(5) // 조회된 데이터의 6번째 라인부터
                        .setMaxResults(8)  // 최대 8개까지 데이터를 조회한다.
                        .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
```

- Detach : 준영속 상태
```java
em.detach(entity) // 특정 엔티티만 준영속 상태로 전환

em.clear() // 영속성 컨텍스트(1차캐시) 초기화

em.close() // 영속성 컨텍스트 종료

entity = em.merge(entity); // 준영속 상태를 다시 영속 상태로 변경 
```
- error handling
```java
public class ErrorHandling {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 트랜잭션당 하나씩 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜잭션시작
            Member member = new Member();
            member.setId(2L); // PK value
            member.setName("Hello");
            em.persist(member);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
```

## 주의점
- 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에 공유
- 엔티티 매니저는 쓰레드간에 공유하지 않는다.
- JPA의 모든 데이터 변경은 트랜잭션 안에서 실행한다.
