package com.bitc.jwtserver.database.dto;

import lombok.*;

import java.util.List;

// JWT 엑세스 토큰 / 리프레시 토큰을 저장한 DTO
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResponseDTO<T> {
    private String accessToken;
    private String refreshToken;
}