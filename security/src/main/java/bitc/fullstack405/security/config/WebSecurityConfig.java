package bitc.fullstack405.security.config;

import bitc.fullstack405.security.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

// WebSecurityConfig : 스프링 시큐리티 설정을 위한 클래스, 사용자가 원하는 클래스명으로 생성하고, @Configuration, @EnableWebSecurity 애너테이션을 추가하여 사용
// 예전에는 WebSecurityConfigurerAdapter 클래스를 상속받아 설정했지만, Spring Security 5(스프링부트 2.7 이후)부터는 WebSecurityConfigurerAdapter를 상속받지 않고 SecurityFilterChain을 사용하는 방식으로 변경됨

@Configuration
// @EnableWebSecurity : 스프링 시큐리티를 활성화하고 사용자 설정을 사용할 수 있도록 하는 애너테이션
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    // WebSecurityCustomizer : 스프링 시큐리티를 사용할 때 WebSecurityCustomizer를 사용하여 보다 세밀한 설정을 할 수 있음
    // WebSecurityCustomizer는 함수형 인터페이스로 configure() 메서드를 오버라이딩하여 설정
    // WebSecurityCustomizer를 사용하면 WebSecurityConfigurerAdapter를 상속받지 않고 설정 가능
    @Bean
    public WebSecurityCustomizer configure() {
        // 스프링 시큐리티 적용 무시 항목을 설정
        // ignoring() : 무시할 목록
        // requestMatchers() : 요청하는 주소와 일치하는 패턴 설정
        // toH2Console() : H2 데이터베이스의 콘솔 주소를 의미
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    // 스프링 시큐리티 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 인증
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/signup", "/user").permitAll() // 전부 허용
                        .anyRequest().authenticated() // 사용자 인증을 받아야 사용할 수 있음
                )
                // Web의 Form을 사용하여 로그인한다는 설정
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/articles")
                        .permitAll()
                )
                // 로그아웃 기능 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .build();
    }

    // 스프링 시큐리티에서는 비밀번호를 암호화하여 저장함
    // BCryptPasswordEncoder : BCrypt 해시 알고리즘을 사용하여 비밀번호를 암호화하는 클래스, 스프링에서 비밀번호 암호화 시 가장 많이 사용하는 클래스
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder, UserDetailService userDetailService) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authProvider);
    }
}
