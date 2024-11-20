package bitc.fullstack405.security.database.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 블로그용 데이터 테이블 Entity
// JPA 영속성 동작
@EntityListeners(AuditingEntityListener.class)
// 데이터베이스 Entity로 사용
@Entity
// 현재 클래스의 테이블명 설정
@Table(name = "article")
// 롬복 라이브러리를 사용하여 getter 메서드 자동 생성
@Getter
// 롬복 라이브러리를 사용하여 모든 매개변수를 가지고 있는 생성자 자동 생성
@AllArgsConstructor
// 롬복 라이브러리를 사용하여 기본 생성자 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 빌더 패턴으로 객체를 생성할 수 있도록 하는 애너테이션
@Builder
public class Article {
    
    @Id
    // 값의 자동 증가 옵션을 데이터베이스 옵션에 의해서 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 컬럼명 'id'로 설정, 수정 금지
    @Column(name = "id", updatable = false)
    private Long id;

    // null 금지
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    // 영속성을 사용하여 현재 시간 입력
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 영속성을 사용하여 마지막 수정 시간 입력
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 생성자 추가, 나머지 값은 자동 생성이므로 빌더 패턴으로 사용 시 입력하지 않아도 됨
    public void updateArticle(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
