package com.bitc.jwtserver.service;

import com.bitc.jwtserver.config.jwt.JwtTokenProvider;
import com.bitc.jwtserver.database.entity.MemberEntity;
import com.bitc.jwtserver.database.entity.RefreshTokenEntity;
import com.bitc.jwtserver.database.repo.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

// 리프레시 토큰을 관리하기 위한 서비스
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 새로운 리프레시 토큰 생성
    public RefreshTokenEntity generateRefreshToken(MemberEntity member) {
        // JWT 토큰을 생성, 토큰 만료 기간을 1일로 설정
        String token = jwtTokenProvider.generateToken(member, Duration.ofDays(1));

        // 리프레시 토큰 엔티티 생성
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
            // 매개변수로 받아온 사용자 정보를 저장
            .member(member)
            // 새로 생성된 리프레시 토큰을 저장
            .refreshToken(token)
            // 만료 시간을 1일로 저장
            .expireDate(LocalDateTime.now().plusDays(1))
            .build();

        // 데이터베이스에 생성된 리프레시 토큰 엔티티 타입의 데이터를 저장하고 반환
        return refreshTokenRepository.save(refreshToken);
    }

    public boolean validateRefreshToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token)
            .map(refreshToken -> !refreshToken.isExpired())
            .orElse(false);
    }

    public void deleteRefreshToken(MemberEntity member) {
    refreshTokenRepository.deleteByMember(member);
  }

    // 데이터베이스에 매개변수로 받은 리프레시 토큰이 저장되어 있는지 확인
    public Optional<MemberEntity> findMemberByToken(String token) {
        // 매개변수로 받은 리프레시 토큰과 동일한 값이 데이터베이스에 있는 확인
        return refreshTokenRepository.findByRefreshToken(token)
            // 검색한 리프레시 토큰의 만료 기간 확인
            .filter(refreshToken -> !refreshToken.isExpired())
            // 리프레시 토큰이 만료되지 않았으면 MemberEntity 객체를 가져옴
            .map(RefreshTokenEntity::getMember);
    }
}