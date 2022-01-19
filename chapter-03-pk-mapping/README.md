# 3. 엔티티 매핑 - 기본키 매핑  

## 기본키 매핑
- @Id만 사용 : 내가 직접 PK값을 만들어서 할당한다.
```java
@Entity
public class Member {

    @Id 
    private Long id;
    //...
```
- IDENTITY : 기본키 생성을 디비(MySQL, PostgreSQL, SQL Sever, DB2)에 위임  
  (ex:mysql auto increment) JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL을 실행하지만  
  AUTO INCREMENT는 데이터베이스에 INSERT SQL을 실행한 후에 ID값을 알수 있기 때문에  
  **이전략을 사용할 경우 em.persist() 시점에 즉시 INSERT SQL을 실행**하고 DB에서 식별자를 조회한다
```java
@Entity
public class Member {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //...
```
- SEQUENCE : 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트  
  오라클, PostegreSQL, DB2, H2등 데이터베이스에서 사용  
  시퀀스 오브젝트를 통해 값을 세팅 후 INSERT SQL 실행
```java
/*
 * @SequenceGenerator를 이용해서 테이블 마다 시퀀스를 생성(이미 생성된)한 후 생성된 시퀀스와 매핑할 수 있다
 */
@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR", 
                   sequenceName = "MEMBER_SEQ", // 매핑할 데이터 베이스 시퀀스 이름
                   initialValue = 1,  //시작값, 기본값은 1 - DDL 생성시에만 사용
                   
                   //시퀀스 한번 호출에 증가하는 수(성능 최적화에 사용됨 데이터베이스  시퀀스 값이 하나씩 증가하도록 설정되어있으면 반드시 1로 설정), 기본값 50
                   //50으로 설정시 미리 시퀀스 50을 생성해서 메모리에 올려놓고 메모리에 1씩 증가하게 된다, 반복적으로 DB에서 다음 시퀀스 호출 횟수를 줄여준다
                   //동시성 이슈없이 다양한 문제가 해결
                   allocationSize = 1
                   )
@Entity
public class Member {

    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    private Long id;
    //...
```

- Table : 키 생성용 테이블 사용하여 시퀀스 전략을 흉내, 모든 DB에서 사용한다. 성능이슈가 있다(다른 전략에 비해 느림)
```sql
-- 키 생성용 테이블
create table MY_SEQUENCES (
 sequence_name varchar(255) not null,
 next_val bigint,
 primary key (sequence_name)
)
```

```java
Entity
@TableGenerator(name = "MEMBER_SEQ_TABLE_GENERATOR", 
                table = "MY_SEQUENCES",
                pkColumnName = "sequence_name",
                pkColumnValue = "MEMBER_SEQ", //sequence_name 컬럼에 들어갈 테이블 전략 식별값

                // 기본값 50
                allocationSize = 1) 
public class Member {
    @Id 
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_TABLE_GENERATOR")
    private Long id;
    //...
```

## 권장 식별자 전략

- 기본키 제약조건 : null 아님, 유일, 변하면 안됨
  - 미래까지 이조건을 만족하는 자연키는 찾기 어려움, 대리키(대체키)를 사용하자
    - 예를 들어 주민등록번호도 기본 키로 적절하지 않음 - 비즈니스를 PK로 끌고 오지 말 것! 
    - 권장 : Long 형 + 대체키(SEQUENCE, UUID) + 키 생성전략 사용