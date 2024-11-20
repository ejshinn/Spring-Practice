package bitc.fullstack405.security.database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 게시물 수정 시 클라이언트에서 서버로 전달하는 데이터 전달용 DTO 클래스
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleRequest {

    private String title;
    private String content;
}
