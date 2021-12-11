# JPA

JPA 정리
* JPA 2.2
* java 버전 11
* 하이버 네이트 5.3.10

# 프로젝트 구성시 참고사항
1. Exception in thread "main" java.lang.NoClassDefFoundError: javax/xml/bind/JAXBException 해결  
자바 9버전 부터 JAXB는 별도의 모듈로 분리되어 JDK에 포함되지 않기 때문에 다음과 같이 그레이들에 의존성을 추가한다.
```gradle
implementation "jakarta.xml.bind:jakarta.xml.bind-api:2.3.2"
implementation "org.glassfish.jaxb:jaxb-runtime:2.3.2"
```


# 챕터
1. hello-jpa 프로젝트 생성과 jpa 프로젝트 구성  



## 참고
 - CustomSqlFunction 호출 방법 - 챕터 13 CustomH2Dialect.java 참고
    - 호출한 SQL 함수가 구현되어 있는 데이터베이스의 방언을 상속한다.
    - 방언을 상속한 커스텀 클래스를 구한하고 클래스의 생성자에서 SQL함수를 registerFunction()을 호출하여 등록한다.
    - persistence.xml에서 방언을 상속한 커스텀 클래스의 패키지 명을 포함한 클래스명을 등록한다.
    - JPQL에서 function('호출할 sql함수명',sql함수파라미터1,sql함수파라미터2...) 형태로 사용한다.