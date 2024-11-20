package bitc.fullstack405.security.service;

import bitc.fullstack405.security.database.domain.Article;
import bitc.fullstack405.security.database.dto.AddArticleRequest;
import bitc.fullstack405.security.database.dto.UpdateArticleRequest;
import bitc.fullstack405.security.database.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    // 블로그 정보를 사용하기 위한 레파지토리 객체 생성
    private final BlogRepository blogRepository;

    // 데이터 저장
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    // 데이터 전체 목록 가져오기
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    // 지정한 데이터 가져오기
    public Article findById(Long id) {
        // Optional : 데이터베이스에서 데이터를 가져올 경우 null 값 때문에 오류가 발생할 수 있으므로 null을 안전하게 처리하기 위한 클래스
        // orElseThrow() : Optional 객체 사용 시 Optional 객체의 값이 존재하지 않을 경우 예외를 출력하는 메서드
        return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }
    
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        article.updateArticle(request.getTitle(), request.getContent());

        return article;
    }
}
