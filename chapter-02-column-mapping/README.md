# 2. 엔티티 매핑 - 컬럼 매핑

## 엔티티 매핑 소개
- 객체와 테이블 매핑 
  - @Entity : JPA를 사용해서 테이블과 매핑할 클래스에 붙인다
    - 기본적인 엔티티명은 클래스명이다
    - 기본생성자 필수(접근제한자public, protected)
    - final 클래스, enum, interface, inner 클래스에 사용하지 않는다
    - 저장할 필드에 final 키워드를 사용하지 않는다.
    - `@Entity(name="엔티티명")` name 속성은 
  - @Table
    - 엔티티와 실제 매핑할 테이블명을 매핑한다 - (@Entity 클래스 이름이 실제 테이블명과 암시적 룰에 의해 일치하면 사용하지 않아도 됨)
    ```java
    @Table("MEMBER")
    @Entity
    class Member {
        //..
    }
    ```
    - `@Table(name="", catalog="", schema="", uniqueConstraints="")` 속성을 쓸 수 있다.
      - name : 매핑할 테이블 명(기본값 : 엔티티명)
      - catalog : 테이터베이스 catalog 매핑
      - schema : 데이터베이스 schema 매핑
      - uniqueConstraints : DDL 생성 시에 유니크 제약 조건 생성
- 필드와 컬럼 매핑: @Column
- 기본 키 매핑: @Id
- 연관관계 매핑 : @ManyToOne, @JoinColumn
## 요구 사항

1. 회원은 일반 회원과 관리자로 구분해야한다.  
2. 회원 가입일과 수정일이 있어야한다.  
3. 회원을 설명할 수 있는 필드가 있어야 한다. 이 필드는 길이 제한이 없다.  

- enum 권한
```java
public enum RoleType {
    USER, ADMIN
}
```
- Member 엔티티
```java
@Entity
public class Member {
   
    @Id 
    private Long id;
    
    /* *
     * insertable, updatable : JPA 상에서 데이터 삽입과 변경 가능여부 , 
     * nullable : null 허용여부 (DDL, DML(하이버네이트) 반영)
     * length : 문자 길이 제약조건 (DDL)
     * unique 제약 조건 걸기 -> 잘 쓰지 않음 하이버네이트가 제약조건 명을 랜덤으로 생상하기 때문
     * 일반적으로 클래스에 설정하는 @Table을 활용헤서 unique 제약조건 설정
     * ex) @Table(uniqueConstraints = {@UniqueConstraint(name = "NAME_UNIQUE", 
     *                 columnNames = {"NAME, AGE"})}) 
     * columnDefinition 디비 컬럼 설정 정보 직접 설정(특정 디비에 종속적인 옵션, DDL) 
     * ex) columnDefinition = "varchar(100) default 'EMPTY'"
     * */
    @Column(name = "NAME", insertable = true, updatable = true, nullable = false)
    private String name;
 
    /**
     * BigDecimal(BigInteger)과 같은 큰 숫자타입의 경우에 사용
     * precision : 소수점 포함 전체 숫자 길이 설정 (DDL)
     * scale : 소수의 자리수 설정 (DDL)
     * 다만 double, float에 적용되지 않는다.
     * 정밀한 소수를 다루어야 할때만 사용
     */
    @Column(precision = 20, scale = 10) 
    private BigDecimal age;
    
    /**
     * EnumType.ORDINAL : enum이 순서를 데이터베이스에 저장 (기본값) - 쓰지말 것 
     * EnumType.STRING : enum의 이룸을 데이터베이스에 저장
     */
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate; //하이버네이트 최신 버전은 LocalDate, LocalDateTime 사용가능
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    
    @Lob
    private String description;
    
    @Transient //엔티티 매핑대상에서 제외되는 필드에 사용
    private int temp;
    
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

    public BigDecimal getAge() {
        return age;
    }

    public void setAge(BigDecimal age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}



```