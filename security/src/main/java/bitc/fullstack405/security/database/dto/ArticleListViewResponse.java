package bitc.fullstack405.security.database.dto;

import bitc.fullstack405.security.database.domain.Article;
import lombok.Getter;

// 서버에서 클라이언트로 블로그 게시물 정보를 리스트 형식으로 전달하기 위한 DTO 클래스
@Getter
public class ArticleListViewResponse {

    private final Long id;
    private final String title;
    private final String content;

    // 빌더 패턴과 stream()의 사용 때문에 만든 생성자
    // @AllArgsConstructor를 사용하지 않은 이유는 stream()의 map() 메서드 사용 시 ArticleListViewResponse::new 형식을 사용하여 Article 클래스 타입의 객체를 받아서 사용하기 위함
    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
