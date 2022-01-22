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

## gradle 설정
```gradle
buildscript {
	repositories {
		mavenCentral()
		jcenter()
	}
	dependencies {
	}
}

ext {
    //springVersion = '5.0.5.RELEASE'
	hibernateVersion = '5.3.10.Final'
	h2Version = '1.4.199' //사용할 h2 데이타베이스 버전과 일치해야함 
	jpaVersion = '2.2'
}

allprojects {}

subprojects {
	apply plugin: 'java'
	apply plugin: 'eclipse'
	apply plugin: 'application'

	sourceCompatibility = "11"
	targetCompatibility = "11"

	[compileJava, compileTestJava]*.options*.encoding = 'UTF-8'

	task initSourceFolders {
		sourceSets*.java.srcDirs*.each {
			if( !it.exists() ) {
				it.mkdirs()
			}
		}
	 
		sourceSets*.resources.srcDirs*.each {
			if( !it.exists() ) {
				it.mkdirs()
			}
		}
	}
	
	repositories {
		mavenCentral()
		jcenter()
	}

	dependencies {
	    //implementation "org.springframework:spring-core:${springVersion}"
	    //implementation "org.springframework:spring-context:${springVersion}"
		//implementation "org.springframework:spring-beans:${springVersion}"
		//implementation "org.springframework:spring-aspects:${springVersion}"

		//JPA
		implementation "org.hibernate:hibernate-entitymanager:${hibernateVersion}"
		implementation "com.h2database:h2:${h2Version}"
		implementation 'javax.persistence:javax.persistence-api:${jpaVersion}'
		implementation "jakarta.xml.bind:jakarta.xml.bind-api:2.3.2"
    	implementation "org.glassfish.jaxb:jaxb-runtime:2.3.2"

	    
		//logging 설정 
		implementation'ch.qos.logback:logback-classic:1.2.3'
	   testImplementation 'junit:junit:4.13'
	}
}
```

## 챕터
- 1.프로젝트 생성과 기본적인 CRUD(JPQL포함)와 플러시, 준영속, 에러처리  
- 2.엔티티 매핑 - 컬럼 매핑  
- 3.엔티티 매핑 - 기본키 매핑  
- 4.실전예제1 - 요구사항 분석과 매핑  
- 5-01. 연관 관계 매핑 기초  
- 5-02. 실전예제2 - 연관관계 매핑  
  
  
  
  
  
  

## 참고
 - CustomSqlFunction 호출 방법 - 챕터 13 CustomH2Dialect.java 참고
    - 호출한 SQL 함수가 구현되어 있는 데이터베이스의 방언을 상속한다.
    - 방언을 상속한 커스텀 클래스를 구한하고 클래스의 생성자에서 SQL함수를 registerFunction()을 호출하여 등록한다.
    - persistence.xml에서 방언을 상속한 커스텀 클래스의 패키지 명을 포함한 클래스명을 등록한다.
    - JPQL에서 function('호출할 sql함수명',sql함수파라미터1,sql함수파라미터2...) 형태로 사용한다.