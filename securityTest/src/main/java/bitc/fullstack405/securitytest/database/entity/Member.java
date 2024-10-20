package bitc.fullstack405.securitytest.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member implements UserDetails {

    // UserDetails 인터페이스를 구현하여 Member 클래스를 스프링 시큐리티에서 사용자 정보를 담고 있는 클래스로 사용
    // UserDetails 인터페이스는 사용자 정보를 담는 인터페이스로 사용자 정보를 담고 있는 객체를 반환하는 메서드를 정의하고 있음
    // 이를 구현하면 스프링 시큐리티에서 사용자 정보를 담고 있는 객체로 사용할 수 있음
    // 클래스명은 사용자가 원하는대로 정하고 UserDetails 인터페이스를 구현하면 됨

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", updatable = false)
    private Long seq;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "user_pw", nullable = false)
    private String userPw;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    // UserDetails에서 상속받은 추상 메서드
    // getAuthorities() : 사용자가 가지고 있는 권한 목록을 출력
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    // getPassword() : 사용자의 비밀번호를 출력
    @Override
    public String getPassword() {
        return userPw;
    }

    // getUsername() : 사용자를 구분하는 유일한 값인 이름(userid, email)을 출력
    @Override
    public String getUsername() {
        return userId;
    }

    // isAccountNonExpired() : 사용자 계정이 만료되지 않았는지를 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // isAccountNonLocked() : 사용자 계정이 잠기지 않았는지를 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // isCredentialsNonExpired() : 사용자 계정의 비밀번호가 만료되지 않았는지를 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // isEnabled() : 사용자 계정이 활성화(사용 가능)인지를 반환
    @Override
    public boolean isEnabled() {
        return true;
    }
}