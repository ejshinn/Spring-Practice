package bitc.fullstack405.security.database.dto;

import bitc.fullstack405.security.database.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 클라이언트와 데이터를 주고 받을 때 Article 엔티티를 직접 사용하지 않고, DTO 클래스를 생성하여 사용
// 클라이언트가 서버로 전달하는 데이터용 DTO 클래스
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleRequest {

    // Article Entity는 id, title, content, createAt, updateAt이라는 5개의 필드가 존재하지만 id, createAt, updateAt 필드는 자동 생성이므로 클라이언트에서 데이터를 받을 필요가 없음
    // 클라이언트에서 입력 받아야 하는 title, content만 사용하는 DTO 클래스를 생성하여 사용
    private String title;
    private String content;

    // DTO 클래스를 입력받은 데이터를 기준으로 Entity 클래스로 변환
    public Article toEntity() {
        // Article article = new Article(title, content);

        // 빌더 패턴을 사용하여 Article 클래스 타입의 객체 생성
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}