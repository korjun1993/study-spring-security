## 스프링 시큐리티 스터디
- 참고자료: [봄이네집 스프링 (1)](https://www.youtube.com/watch?v=SMZm2aqI_dQ&list=PLcsqrv8NxApXzHViDU2fB1ew7KoLoaB02)
  - 저자: [봄이네집 개발 블로그](https://tech.wheejuni.com/)
- 참고자료: [Spring security 아키텍처 이해 - 인증 흐름](https://velog.io/@dailylifecoding/spring-security-authentication-process-flow) 
  - 저자: [dailylifecoding](velog.io/@dailylifecoding) 

## 우리가 구현해야 할 것
- 요청을 받아낼 필터 (AbstractAuthenticationFilter)
- Manager에 등록시킬 Auth Provider
- AJAX 방식이라면, 인증 정보를 담을 DTO
- 각 인증에 따른 추가 구현체. 기본적으로 성공/실패 핸들러
- 소셜 인증의 경우 각 소셜 공급자의 규격에 맞는 DTO와 HTTP req 객체
- 인증 시도 / 인증 성공시에 각각 사용할 Authentication 객체

## 유저 정보를 인증 과정에서 처리하는 방식
- 유저 모델을 그대로 사용
- 유저 디테일즈 구현체를 사용

## 기본적인 유저 정보
- 아이디
- 비밀번호
- 이름
- 프로필 사진 링크
- 서비스상에서 유저에게 부여하고 싶은 권한
- 소셜 로그인한 사용자의 경우, 소셜 서비스 부여한 ID 코드 **(로그인 ID 아님)**

## 수업에서 다루는 Issue
### @Enumerated(value = EnumType.STRING)을 사용하자
- Account.userRole 필드는 Enum 타입이다.
```java
public enum UserRole {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private String roleName;

    private UserRole(String roleName) {
        this.roleName = roleName;
    }
}
```
- Account.userRole 필드에 @Enumerated 애노테이션을 붙인다.
```java
public class Account {
  @Column(name = "ACCOUNT_ROLE")
  @Enumerated(value = EnumType.ORDINAL)
  private UserRole userRole;
}
```
- 필드 값은 숫자로 매칭되어 데이터베이스에 들어간다.
  - "ADMIN" → 0
  - "USER" → 1
- 코드에서 "ADMIN", "USER" 순서가 바뀌면 어떻게 될까?
```java
public enum UserRole {
  USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private String roleName;

    private UserRole(String roleName) {
        this.roleName = roleName;
  }
}
```
- 데이터베이스상으로 컬럼값이 1인 Account 객체는 본래 "USER"이어야 하지만, "ADMIN"이 되버린다.
- 따라서, @Enumerated(value = EnumType.STRING) 으로 구현하자.
---
### 사용자 객체 이름을 User로 짓지 말자
- 외부라이브러리에 User라는 이름의 객체가 많다. 스프링에서도 User 객체가 존재한다.
- 본 프로젝트에서는 User라는 이름의 UserDetails 인터페이스를 구현한 객체를 사용한다.
