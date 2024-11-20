package bitc.fullstack405.security.database.dto;

import bitc.fullstack405.security.database.domain.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 서버에서 클라이언트로 지정한 블로그 게시물 정보를 전달하기 위한 DTO 클래스
@Getter
@NoArgsConstructor
public class ArticleViewResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt; // 최종 수정 시간

    public ArticleViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
    }
}