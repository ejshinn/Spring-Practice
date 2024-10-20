package bitc.fullstack405.securitytest.database.repository;

import bitc.fullstack405.securitytest.database.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository를 상속받아 사용함
public interface MemberRepository extends JpaRepository<Member, Long> {

    // JpaRepository에서 제공하는 쿼리 메서드를 사용
    // 반환값을 Optional 타입으로 가져옴
    Optional<Member> findByUserId(String userId);
}
