package bitc.fullstack405.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // addCorsMappings() : CORS 설정을 추가하는 메서드
    // CorsRegistry : CORS 설정을 정의하고 적용하기 위한 매개변수용 클래스
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // addMapping() : CORS 설정을 적용할 URL 패턴을 지정
        // allowedOrigins() : 허용할 URL 지정
        // allowedMethods() : 허용할 HTTP 접속 방식을 지정
        // allowedHeaders() : 허용할 HTTP 헤더를 지정, 모든 헤더를 허용 시 * 사용, 여러 헤더 사용 시 배열로 지정
        // exposedHeaders() : 브라우저가 접근할 수 있는 헤더를 지정
        // allowCredentials() : 쿠키를 주고 받을 수 있도록 설정
        // maxAge() : 브라우저가 preflight 요청을 캐시할 시간을 지정
        registry.addMapping("/api/**") // /api/** 경로에 대한 CORS 설정
                .allowedOrigins("http://localhost:8080") // 허용할 출처
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 모든 헤더 허용
                .exposedHeaders("Authorization") // 노출할 응답 헤더
                .allowCredentials(true) // 자격 증명 허용
                .maxAge(3600); // 요청 캐시 시간을 1시간으로 설정
    }
}