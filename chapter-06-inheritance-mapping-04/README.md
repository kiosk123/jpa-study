# 6-04. 상속관계 매핑 - 공통 매핑 정보를 상위 클래스로 빼기 `@MappedSuperclass`   

 DB상 상속관계는 아니지만, 객체상에서 필드가 반복될 경우, 반복되는 필드만 상위클래스로 빼는 방법  
 `@MappedSuperclass`를 사용

![.](./img/1.png)  
![.](./img/2.png)  
![.](./img/3.png)  

## 매핑1 - 공통적인 필드명이 같아서 상위클래스로 빼는 경우

**BaseEntity** 클래스
```java
@MappedSuperclass //매핑정보만 받는 슈퍼클래스
public abstract class BaseEntity {
    private String createBy;
    private LocalDateTime createTime;
    private String lastModified;
    private LocalDateTime lastModifiedDate;

    // 생략..
}
```

**Member** 엔티티

```java
@Entity
public class Member extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private Long name;

    // 생략..
}
```

**Team** 엔티티
```java
@Entity
public class Team extends BaseEntity {
    
    @Id @GeneratedValue
    private Long id;
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    
    // 생략..
}
```


## 매핑2 - 공통적인 필드명에 PK가 포함되어서 PK필드 포함하여 상위클래스로 빼는 경우

**BaseEntity2** 클래스
```java
@MappedSuperclass
public abstract class BaseEntity2 {
    
    @Id @GeneratedValue
    private Long id;
    private String name;

    // 생략..
}

```

**Member2** 엔티티
```java
@Entity
public class Member2 extends BaseEntity2 {
    private String email;
}
```

**Seller** 엔티티
```java
@Entity
public class Seller extends BaseEntity2 {
    private String shopName;
}
```

## 주의 사항
 `@MappedSuperclass` 로 매핑한 클래스는 다음과 같은 방식으로 **조회 하지 말 것**

```java
BaseEntity entity = em.find(BaseEntity.class, 13L); //(X)
```