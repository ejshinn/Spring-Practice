package com.bitc.jwtserver.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

// 사용자 정보를 저장할 MemberEntity 클래스, UserDetails를 상속받아 구현함
// UserDetails : 스프링 시큐리티에서 사용하는 사용자 인증 정보가 포함된 인터페이스
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class MemberEntity implements UserDetails {

    // 회원의 순번으로 사용할 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    // 사용자 ID
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    // 사용자 비밀번호, password 는 스프링 시큐리티에서 기본적으로 사용하는 비밀번호의 이름
    @Column(name = "user_pw", nullable = false)
    private String password;

    // 컬럼명을 userName으로 설정, 스프링 시큐리티에서 내부적으로 사용자 정보를 확인하기 위한 이름이 username으로 설정되어 있기 때문에 getUserName() 메서드가 getUsername()로 인식되는 문제가 있기 때문에 userNick을 사용함
    @Column(name = "user_nick")
    private String userNick;

    @Column(name = "user_email")
    private String userEmail;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 사용자 권한 출력
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    // 사용자 ID 출력, username이 스프링 시큐리티에서 사용자를 구분하는 이름으로 지정되어 있기 때문에 getUsername()은 사용자 ID를 출력하는 메서드로 무조건 사용
    @Override
    public String getUsername() {
    return userId;
  }

    // 사용자 비밀번호 출력, password가 스프링 시큐리티에서 사용자 비밀번호의 이름으로 지정되어 있기 때문에 getPassword()를 사용자 비밀번호를 출력하는 메서드로 무조건 사용
    @Override
    public String getPassword() {
    return password;
  }

    @Override
    public boolean isAccountNonExpired() {
    return true;
  }

    @Override
    public boolean isAccountNonLocked() {
    return true;
  }

    @Override
    public boolean isCredentialsNonExpired() {
    return true;
  }

    @Override
    public boolean isEnabled() {
    return true;
  }
}