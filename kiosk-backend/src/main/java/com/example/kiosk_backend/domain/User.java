package com.example.kiosk_backend.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
// 엔티티 클래스가 users라는 이름의 데이터베이스 테이블과 매핑됨을 나타냅니다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 파라미터가 없는 기본 생성자를 생성,접근 수준은 PROTECTED로 설정
@Getter
@Entity
// 이 클래스가 JPA 엔티티임을 나타냅니다.
public class User implements UserDetails {
    // @Id는 이 필드가 이 Entity의 Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Primary Key의 생성 전략을 명시, DB가 알아서 ID를 생성하도록
    @Column(name = "id", updatable = false)
    // 필드가 DB 테이블의 id 컬럼에 매핑되며, 이 컬럼은 수정 불가능(updatable = false)하다는 것
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "auth")
    private String auth; // ✅ 권한 정보 필드 추가 ("user", "admin" 등)

    // 불변성을 보장하는 복잡한 객체 생성 코드를 간결하게 만들 수 있습니다.
    @Builder
    public User(String email, String password, String auth) {
        this.email = email;
        this.password = password;
        this.auth = auth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + auth.toUpperCase()));
    }
    // 상속하는 모든 클래스를 허용,사용자에게 부여된 권한 목록을 반환합니다
    // 객체를 생성하고 이를 담은 리스트를 반환합니다. 이것은 이 User 객체가 "user"라는 권한을 가지고 있음
    // 풀어서 해석하면? user"라는 이름의 권한을 가진 SimpleGrantedAuthority 객체를 생성하고,
    // 이를 List에 담아 리턴하고 있다,이 사용자는 user라는 권한을 가지고 있다를 알수가 있다

    @Override
    public String getUsername() {
        return email;
    }// 사용자의 id를 반환

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }// 만료 여부 반환

    @Override
    public boolean isAccountNonLocked() {
        return true;// 잠금 되지 않앗음
    }// 계정 잠금이 되었는지 확인하는 로직

    @Override
    public boolean isCredentialsNonExpired() {
        return true;// 만료되지 않앗음
    }// 패스워드가 만료되었는지 확인하는 로직

    @Override
    public boolean isEnabled() {
        return true;// 사용가능
    }// 계정이 사용이 가능한지 확인하는
}
