package com.bitc.jwtserver.database.repo;

import com.bitc.jwtserver.database.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    // 클라이언트가 전달한 userId를 가지고 사용자 검색
    Optional<MemberEntity> findByUserId(String userId);
    // 해당 사용자가 존재하는지 여부 확인
    boolean existsByUserId(String userId);
    // 해당 email이 존재하는지 여부 확인
    boolean existsByUserEmail(String email);
}