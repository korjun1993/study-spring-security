## 스프링 시큐리티 스터디
- 참고자료 : 봄이네집 스프링 (1)

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
