# 13. JPQL 객체 지향 쿼리 언어

![.](./img/1.png)  
![.](./img/2.png)  
![.](./img/3.png)  
![.](./img/4.png)  
![.](./img/5.png)  
![.](./img/6.png)  
![.](./img/7.png)  
![.](./img/8.png)  
![.](./img/9.png)  
![.](./img/10.png)  
![.](./img/11.png)  
![.](./img/12.png)  
![.](./img/13.png)  
![.](./img/14.png)  
![.](./img/15.png)  
![.](./img/16.png)  
![.](./img/17.png)  
![.](./img/18.png)  
![.](./img/19.png)  
![.](./img/20.png)  
![.](./img/21.png)  
![.](./img/22.png)  
![.](./img/23.png)  
![.](./img/24.png)  
![.](./img/25.png)  
![.](./img/26.png)  
![.](./img/27.png)  
![.](./img/28.png)  
![.](./img/29.png)  
![.](./img/30.png)  
![.](./img/31.png)  
![.](./img/32.png)  
![.](./img/33.png)  
![.](./img/34.png)  
![.](./img/35.png)  
![.](./img/36.png)  
![.](./img/37.png)  
![.](./img/38.png)  
![.](./img/39.png)  
![.](./img/40.png)  
![.](./img/41.png)  
![.](./img/42.png)  
![.](./img/43.png)  
![.](./img/44.png)  
![.](./img/45.png)  
![.](./img/46.png)  
![.](./img/47.png)  
![.](./img/48.png)  
![.](./img/49.png)  
![.](./img/50.png)  
![.](./img/51.png)  
![.](./img/52.png)  
![.](./img/53.png)  
![.](./img/54.png)  
![.](./img/55.png)  
![.](./img/56.png)  
![.](./img/57.png)  
![.](./img/58.png)  
![.](./img/59.png)  
![.](./img/60.png)  
![.](./img/61.png)  
![.](./img/62.png)  
![.](./img/63.png)  
![.](./img/64.png)  
![.](./img/65.png)  
![.](./img/66.png)  
![.](./img/67.png)  
![.](./img/68.png)  
![.](./img/69.png)  
![.](./img/70.png)  
![.](./img/71.png)  
![.](./img/72.png)  
![.](./img/73.png)  
![.](./img/74.png)  

## N + 1 문제 해결을 위한 설정
fetch 조인을 사용하지 않고 Lazy로딩으로 설정된 일대다 양방향 매핑을 리스트를 가져올 때
사용할때 최적하 하는 방법이다.  
  
값을 설정 함으로써 실제 IN 쿼리를 이용하여 값만큼 메모리에 미리 로드 시켜 놓기 때문에  
DB에 쿼리를 던지는 횟수가 줄어든다.  

**설정 방법**
- 엔티티 매핑 리스트에 `@BatchSize`를 이용한 로컬 세팅 
```java
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
```
- `persistence.xml`에 글로벌 세팅하기

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2" 
    xmlns="http://xmlns.jcp.org/xml/ns/persistence"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">

	<persistence-unit name="H2">
	
	    <!-- import할 NamedQuery가 설정된 xml 파일 경로 지정 -->
	    <mapping-file>META-INF/ormMember.xml</mapping-file>
	    <mapping-file>META-INF/ormTeam.xml</mapping-file>
	    
		<properties>
			<property name="javax.persistence.jdbc.driver" value="org.h2.Driver" />
			<property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test" />
			<property name="javax.persistence.jdbc.user" value="sa" />
			<property name="javax.persistence.jdbc.password" value="" />
			
			<!-- 하이버네이트 방언 설정 -->
			<property name="hibernate.dialect" value="com.jpa.study.dialect.CustomH2Dialect" />
			
			<property name="hibernate.show_sql" value="true" />
			<property name="hibernate.format_sql" value="true"/>
			<property name="hibernate.use_sql_comments" value="true"/>

			<property name="hibernate.hbm2ddl.auto" value="create" />
			
			<!-- @BatchSize의 글로벌 세팅 -->
			<property name="hibernate.default_batch_fetch_size" value="100"/>

		</properties>

	</persistence-unit>
</persistence>
```