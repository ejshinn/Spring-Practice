package bitc.fullstack405.securitytest.service;

import bitc.fullstack405.securitytest.database.entity.Member;
import bitc.fullstack405.securitytest.database.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// UserDetailsService를 구현한 클래스를 만들어서 loadUserByUsername() 메서드를 구현하면 사용자 인증을 처리할 수 있음
// UserDetailsService : 스프링 시큐리티에서 사용자 인증을 처리하는 인터페이스
@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // loadUserByUsername : UserDetailsService 인터페이스에서 제공하는 사용자 인증 메서드, 반환값으로 UserDetails 인터페이스 타입의 객체를 반환
    // 사용자 이름을 받아서 UserDetails 타입의 객체를 반환
    // username이 스프링 시큐리티 내부적으로 사용하는 사용자를 구분하는 이름
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 해당 메서드를 오버라이딩하여 데이터베이스에서 지정한 사용자 정보를 가져옴
        // repository를 사용하여 데이터베이스에서 지정한 사용자 정보를 가져옴
        // orElseThrow() : Optional 타입으로 데이터를 가져올 경우 데이터가 있으면 지정한 클래스 타입으로 데이터를 출력하고, 없을 경우 예외 발생
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId + "라는 사용자가 없습니다."));

        return member;
    }
}