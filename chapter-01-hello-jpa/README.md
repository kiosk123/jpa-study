1. hello-jpa

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
src/main/resources/META/persistence.xml 파일을 생성해야 한다.