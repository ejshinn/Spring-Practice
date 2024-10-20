package bitc.fullstack405.securitytest.config;

import bitc.fullstack405.securitytest.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

// 인증 : 사용자가 맞는지 확인
// 인가 : 사용 권한이 있는가 확인

// 스프링 시큐리티 설정을 위한 클래스, 사용자가 원하는 클래스명을 사용하고 @Configuration, @EnableWebSecurity 애너테이션을 추가하여 사용
// 스프링부트 3 버전에서는 @EnableWebSecurity가 자동으로 동작함, 생략해도 상관없음
// @EnableWebSecurity : 스프링 시큐리티를 활성화하고 사용자 설정을 사용할 수 있도록 하는 애너테이션
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    // WebSecurityCustomizer : 스프링 시큐리티 사용 시 세밀한 설정을 하기 위한 클래스
    // WebSecurityCustomizer는 함수형 인터페이스로, 현재는 WebSecurityConfigurerAdapter를 상속받지 않고 사용할 수 있음
    // ignoring() : 스프링 시큐리티에서 제외할 목록
    // requestMatchers() : URL 목록
    // toH2Console() : H2 데이터베이스 콘솔 URL
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.ignoring()
                .requestMatchers(toH2Console()) // H2 안 쓸 경우 이 부분만 삭제하면 됨
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    // SecurityFilterChain : 스프링 시큐리티의 필터 체인을 설정
    // 필터 체인은 여러 개의 필터를 순차적으로 실행하는데, 필터 체인을 설정하면 필터 체인을 통해서 요청을 처리할 수 있음
    // HttpSecurity : 스프링 시큐리티에서 HTTP 요청에 대한 보안 설정을 하는 클래스
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // csrf : cross site request forgery, 웹의 취약점을 악용하여 사용자의 권한을 도용하여 악의적인 요청을 서버로 보내는 공격 기법, 스프링 시큐리티는 csrf 보안을 기본적으로 켜놓고 있음

        // 스프링 6에서는 HttpSecurity를 설정하는 방법이 람다식(화살표 함수)을 사용하는 방식으로 변경됨

        // authorizeHttpRequests : HTTP 요청에 대한 보안 설정
        // requestMatchers() : 요청에 대한 보안 설정을 하는 메서드, 문자열로 URL을 입력 받음, 여러 개의 URL을 한 번에 설정할 수 있음, 여러 개의 requestMatchers() 메서드를 사용할 수 있음
        // anyRequest() : 모든 요청에 대한 보안 설정을 하는 메서드, requestMatchers()로 지정한 보안 설정을 제외한 모든 요청에 대한 보안 설정을 진행하는 메서드
        // permitAll() : 모든 사용자에게 접근을 허용하는 메서드
        // authenticated() : 인증된 사용자에게만 접근을 허용하는 메서드
        // hasRole() : 특정 권한을 가진 사용자에게만 접근을 허용하는 메서드, 권한명을 문자열로 입력, 1개의 권한을 가지고 있을 경우 사용
        // hasAnyRole() : 여러 권한 중 하나라도 가지고 있는 사용자에게 접근을 허용하는 메서드
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        // 지정한 여러 개의 URL에 대해서 모든 사용 권한을 허용
                        .requestMatchers("/", "/public", "/login", "/signup").permitAll()
                        // /admin/** URL에 대해서는 ADMIN 권한을 가진 사용자만 허용
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // /member/** URL에 대해서는 ADMIN, MEMBER 권한을 가진 사용자만 허용
                        .requestMatchers("/member/**").hasAnyRole("MEMBER", "ADMIN")
                        // 인증 정보가 있어야 사용 가능
                        .anyRequest().authenticated()
                )
                // formLogin : 사용자 인증을 위한 로그인 시 form 사용 설정
                // loginPage : 로그인을 위해서 사용하는 URL을 설정, 로그인 페이지를 직접 만들어 사용 시 사용
                // loginProcessingUrl() : 로그인 처리 URL을 설정, 로그인 프로세스 페이지를 직접 만들어 사용 시 사용
                // defaultSuccessUrl() : 로그인 성공 후 이동(리다이렉트)할 페이지 설정, 첫 번째 매개변수는 이동할 URL, 두 번째 매개변수는 boolean 입력(true : 지정한 URL로 이동, false : 이전 페이지로 이동)
                // successForwardUrl() : 로그인 성공 후 이동(포워드)할 페이지 설정, 서버에서 추가적인 처리를 해야 할 경우 사용
                // failureUrl() : 로그인 실패 후 이동(리다이렉트)할 페이지 설정, 로그인 실패 페이지를 직접 만들어 사용 시 사용
                // failureForwardUrl() : 로그인 실패 후 이동(포워드)할 페이지 설정, 서버에서 추가적인 처리를 해야할 경우 사용
                // usernameParameter() : 로그인 페이지에서 사용자 이름을 입력하는 input 태그의 name 속성을 설정하는 메서드, 스프링 시큐리티의 기본값은 username임
                // passwordParameter() : 로그인 페이지에서 사용자 이름을 입력하는 input 태그의 name 속성을 설정하는 메서드, 스프링 시큐리티의 기본값은 password임
                // failureHandler() : 로그인 실패 시 커스텀 실패 핸들러를 지정할 수 있음, 로그인 실패 시 로직을 세밀하게 제어하기 위해 사용
                // successHandler() : 로그인 성공 시 커스텀 성공 핸들러를 지정할 수 있음, 로그인 성공 시 추가적인 처리를 세밀하게 제어하기 위해 사용

//                ex)
//                .formLogin(login -> login
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/")
//                        .failureUrl("/login/error")
//                        .usernameParameter("userId")
//                        .passwordParameter("userPw")
//                        .permitAll()
//                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .usernameParameter("userId")
                        .passwordParameter("userPw")
                        .permitAll()
                )
                // logout : 사용자 로그아웃 설정을 위해서 사용
                // logoutUrl() : 로그아웃 URL을 설정, 사용자가 직접 로그아웃 URL을 만들어 사용 시 사용
                // logoutSuccessUrl() : 로그아웃 성공 시 이동(리다이렉트)할 페이지를 설정
                // logoutSuccessHandler() : 로그아웃 성공 시 커스텀 핸들러를 지정할 수 있음, 로그아웃 성공 시 추가적인 처리를 위해서 사용
                // invalidateHttpSession() : 로그아웃 시 세션을 무효화할지 여부를 설정, 기본값 true
                // deleteCookies() : 로그아웃 시 삭제할 쿠키를 설정, 쿠키 이름을 문자열로 입력하여 삭제할 수 있음
                // clearAuthentication() : 로그아웃 시 인증 정보를 삭제할지 여부를 설정하는 메서드, 기본값 true
                // Customizer.withDefaults() : 스프링 시큐리티 기본 설정 사용

//                ex)
//                .logout(logout -> logout
//                        .logoutUrl("/perform_logout") // 로그아웃 요청을 받을 URL
//                        .logoutSuccessUrl("/login?logout") // 로그아웃 성공 후 리다이렉트 URL
//                        .invalidateHttpSession(true) // 로그아웃 시 세션 무효화
//                        .clearAuthentication(true) // 인증 정보 제거
//                        .deleteCookies("JSESSIONID") // 로그아웃 시 쿠키 삭제
//                )
                .logout(Customizer.withDefaults());

        return http.build();
    }

    // 스프링 시큐리티에서는 비밀번호를 반드시 암호화하여 저장해야 함
    // BCryptPasswordEncoder : BCrypt 해시 알고리즘을 사용하여 비밀번호를 암호화하는 클래스
    // 스프링 시큐리티에서 비밀번호 암호화 시 가장 많이 사용함
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인증 관련 설정
    // 간단한 인증 요구 시 UserDetailsService를 구현한 클래스를 빈으로 등록하여 사용
    // 복잡한 인증 요구 시 AuthenticationManager를 구현한 클래스를 빈으로 등록하여 사용
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder, MemberDetailsService memberDetailsService) throws Exception {
        // DaoAuthenticationProvider : 데이터베이스 정보를 사용하여 사용자 인증을 진행하는 클래스
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 사용자 인증 정보를 저장
        authProvider.setUserDetailsService(memberDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authProvider);
    }
}
