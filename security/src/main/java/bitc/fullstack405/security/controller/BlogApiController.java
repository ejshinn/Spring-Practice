package bitc.fullstack405.security.controller;

import bitc.fullstack405.security.database.domain.Article;
import bitc.fullstack405.security.database.dto.AddArticleRequest;
import bitc.fullstack405.security.database.dto.ArticleResponse;
import bitc.fullstack405.security.database.dto.UpdateArticleRequest;
import bitc.fullstack405.security.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogApiController {

    private final BlogService blogService;

    // 블로그 글 등록
    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        // 클라이언트에서 전달받은 데이터로 데이터베이스에 저장
        // 저장된 데이터 정보를 그대로 다시 받아옴
        Article savedArticle = blogService.save(request);

        // ResponseEntity : 클라이언트에게 전달하는 데이터와 http 상태 및 헤더 정보를 가지고 있는 클래스
        // status() : 클라이언트에게 응답할 http 상태 정보
        // body() : 클라이언트에게 전달할 데이터
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    // 블로그 글 목록 출력
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticle() {
        List<ArticleResponse> articleList = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        // ResponseEntity.ok() : ResponseEntity의 기본형은 ResponseEntity.status()를 사용하는 것이지만 통신 성공을 뜻하는 ok는 워낙 많이 사용하기 때문에 따로 메서드를 만들어줌
        // ResponseEntity.status(HttpStatus.OK).body(articleList);
        return ResponseEntity.ok().body(articleList);
    }

    // 블로그 글 상세보기
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id) {
        Article article = blogService.findById(id);

        // ResponseEntity.ok().body(데이터)의 줄임 형태
        return ResponseEntity.ok(new ArticleResponse(article));
    }

    // 블로그 글 삭제
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.delete(id);

        // ResponseEntity<Void> : ResponseEntity 사용 시 body()를 사용하여 데이터를 전달해야 하지만 delete처럼 전달할 데이터가 없을 경우 ResponseEntity<Void>로 반환 타입을 설정
        // ResponseEntity<Void>를 통해서 반환할 데이터가 없을 경우 ResponseEntity.ok().build()를 사용
        return ResponseEntity.ok().build();
    }

    // 블로그 글 수정
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);

        // 데이터의 등록이나 수정 시 반환 타입으로 ResponseEntity<클래스>를 사용한 이유는 데이터 등록 및 수정 후 클라이언트에서 전달한 데이터와 데이터베이스에 등록된 데이터가 동일한 데이터인지 확인을 위해서 추가한 것, 필수는 아님
        return ResponseEntity.ok(updatedArticle);
    }
}