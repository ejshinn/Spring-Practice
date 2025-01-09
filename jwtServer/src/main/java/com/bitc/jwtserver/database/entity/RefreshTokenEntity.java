package com.bitc.jwtserver.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// 리프레시 토큰을 데이터베이스에 저장하기 위해서 사용하는 엔티티 클래스
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    // MemberEntity와 1 : N 관계에 있으며, 지연 적용 방식을 사용, MemberEntity의 user_id 컬럼과 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MemberEntity member;

    // @Lob : 해당 컬럼의 크기를 255 이상의 데이터를 입력할 수 있도록 하는 애너테이션
    // 리프레스 토큰을 저장하기 위한 컬럼, 리프레시 토큰의 경우 문자열의 길이가 255를 넘는 경우가 많기 때문에 컬럼의 최대 크기를 255보다 큰 값을 저장할 수 있도록 설정하여 사용
    @Lob
    @Column(nullable = false)
    private String refreshToken;

    // 만료 기간
    @Column(nullable = false)
    private LocalDateTime expireDate;

    // 추가로, 발행된 IP나 기기 정보 등을 저장할 수 있음
    // private String ipAddress;
    // private String userAgent;

    // 리프레시 토큰의 만료 시간을 초과 했는지 확인
    public boolean isExpired() {
    return LocalDateTime.now().isAfter(this.expireDate);
  }
}