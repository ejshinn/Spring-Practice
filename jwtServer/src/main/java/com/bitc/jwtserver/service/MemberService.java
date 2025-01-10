package com.bitc.jwtserver.service;

import com.bitc.jwtserver.config.jwt.JwtTokenProvider;
import com.bitc.jwtserver.database.dto.MemberDTO;
import com.bitc.jwtserver.database.dto.ResponseDTO;
import com.bitc.jwtserver.database.entity.MemberEntity;
import com.bitc.jwtserver.database.entity.RefreshTokenEntity;
import com.bitc.jwtserver.database.entity.Role;
import com.bitc.jwtserver.database.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

// 회원가입 및 인증을 위한 서비스
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    // 사용자 인증(로그인), 클라이언트에서 전달받은 userId / userPw를 사용하여 해당 id/pw를 사용하는 정보가 있는지 확인
    public ResponseDTO getJwtAuthenticate(String userId, String userPw) {

        // Authentication : 스프링 시큐리티에서 제공하는 사용자 인증 정보 및 인증 상태를 가지고 있는 인터페이스
        // getPrincipal() : 인증된 사용자 정보를 출력 (UserDetails를 상속받은 클래스 객체)
        // getCredentials() : 인증 정보를 출력 (비밀번호)
        // isAuthenticated() : 현재 인증 상태를 출력

        // AuthenticationManager : 스프링 시큐리티에서 실제 인증 작업을 수행하는 인터페이스
        // authenticate() : 사용자의 인증정보를 받아 처리하고 성공 시 Authentication 타입의 객체를 출력

        // UsernamePasswordAuthenticationToken : 스프링 시큐리티에서 가장 일반적으로 사용하는 인증 정보를 담는 객체, 주로 사용자 ID / PW 를 기반으로 인증 시 사용함
        // 첫 번째 매개변수로 사용자 ID, 두 번째 매개변수로 사용자 PW 를 입력
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userId, userPw));

        // 반환 타입이 Object 타입이므로 사용자 정보 엔티티인 MemberEntity로 강제 타입 변환하여 사용자 데이터를 가져옴
        MemberEntity member = (MemberEntity) authentication.getPrincipal();

        // JWT를 사용하여 JWT 엑세스 토큰을 생성, 엑세스 토큰 사용 만료 시간을 1분으로 설정
        String accessToken = jwtTokenProvider.generateToken(member, Duration.ofMinutes(1));
        // JWT를 사용하여 JWT 리프레시 토큰을 생성
        RefreshTokenEntity refreshToken = refreshTokenService.generateRefreshToken(member);

        // 만들어진 JWT 엑세스 토큰 및 리프레시 토큰을 ResponseDTO 타입의 객체에 저장하여 반환
        return ResponseDTO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken.getRefreshToken())
            .build();
    }

    // 회원가입
    public String signupMember(MemberDTO member) {
        // 기존 사용자 ID가 있는지 확인
        if (memberRepository.existsByUserId(member.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다.");
        }

        // 기존 사용자 Email이 있는지 확인
        if (memberRepository.existsByUserEmail(member.getUserEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 클라이언트에서 입력받은 사용자 비밀번호를 PasswordEncoder를 통해서 암호화, 스프링 시큐리티에서는 비밀번호를 반드시 암호화해서 저장해야 함
        String encodedPassword = passwordEncoder.encode(member.getUserPw());

        // 사용자 정보를 데이터베이스에 저장하기 위해서 MemberEntity 타입의 객체로 생성
        MemberEntity newMember = MemberEntity.builder()
            .userId(member.getUserId())
            // 암호화된 비밀번호 저장
            .password(encodedPassword)
            .userEmail(member.getUserEmail())
            .userNick(member.getUserNick())
            // 사용자 권한으로 ROLE_MEMBER를 기본으로 사용, 다른 권한을 사용하고자할 경우 다른 로직에서 권한 추가
            .role(Role.ROLE_MEMBER)
            .build();

        // 사용자 정보를 데이터베이스에 저장
        memberRepository.save(newMember);

        return "회원가입 성공";
    }

    // 사용자 삭제
    @Transactional
    public void deleteMember(String userId) {
        // 사용자가 있으면 사용자 정보를 가져오고, 없으면 오류 메시지 출력
        MemberEntity member = memberRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 사용자를 데이터베이스에서 삭제
        memberRepository.delete(member);
    }

    // 데이터베이스에 리프레시 토큰이 존재하는지 여부 확인 및 새로운 엑세스 토큰 생성
    public ResponseDTO refreshAccessToken(String refreshToken) {

        // 데이터베이스에 리프레시 토큰이 있을 경우 엑세스 토큰을 새로 발급하고, 없으면 오류 발생
        String newAccessToken = refreshTokenService.findMemberByToken(refreshToken)
            .map(member -> jwtTokenProvider.generateToken(member, Duration.ofMinutes(1)))
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않거나 만료된 리프레시 토큰입니다."));

        // 새로 만들어진 엑세스 토큰을 클라이언트로 전달하기 위해서 ResponseDTO 타입의 객체를 생성하고 accessToken 필드에 엑세스 토큰을 저장
        // 리프레시 토큰을 기존의 것을 그대로 사용할 것이기 때문에 추가하지 않음
        return ResponseDTO.builder()
            .accessToken(newAccessToken)
            .build();
    }
}