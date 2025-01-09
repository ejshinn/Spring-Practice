package com.bitc.jwtserver.database.repo;

import com.bitc.jwtserver.database.entity.MemberEntity;
import com.bitc.jwtserver.database.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    // 지정한 리프레시 토큰을 검색하여 가져오기
    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
    // 지정한 사용자 삭제
    void deleteByMember(MemberEntity member);
}