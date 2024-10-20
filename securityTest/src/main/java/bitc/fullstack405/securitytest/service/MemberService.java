package bitc.fullstack405.securitytest.service;

import bitc.fullstack405.securitytest.database.entity.Member;
import bitc.fullstack405.securitytest.database.entity.Role;
import bitc.fullstack405.securitytest.database.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signupMember(String userId, String userPw, String userName, String userEmail) {
    // 권한 설정
    Role userRole = userId.toLowerCase().contains("admin") ? Role.ROLE_ADMIN : Role.ROLE_MEMBER;
    // 비밀번호 암호화
    String encodePassword = passwordEncoder.encode(userPw);

    // 빌더 패턴으로 사용자 생성
    Member newMember = Member.builder()
        // id는 클라이언트에서 전달된 id 그대로 사용
        .userId(userId)
        // 암호화된 비밀번호 사용
        .userPw(encodePassword)
        .userName(userName)
        .userEmail(userEmail)
        // 지정한 권한 사용
        .role(userRole)
        .build();

    // 데이터베이스에 저장
    memberRepository.save(newMember);
    }
}