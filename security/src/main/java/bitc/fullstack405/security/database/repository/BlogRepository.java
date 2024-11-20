package bitc.fullstack405.security.database.repository;

import bitc.fullstack405.security.database.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

// JPA를 사용하여 블로그의 내용을 컨트롤하는 repository 인터페이스
// JPA에서 제공하는 기본 기능만 사용 예정
public interface BlogRepository extends JpaRepository<Article, Long> {
    
}