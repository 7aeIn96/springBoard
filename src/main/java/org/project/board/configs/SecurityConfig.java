package org.project.board.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 설정 클래스
public class SecurityConfig {
    @Bean // 스프링 관리 객체
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // 로그인, 로그아웃 url 구성
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // 정적 자원 시큐리티 무력화 ( 접속마다 비밀번호 없이 접근 가능 )
        return w -> w.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/errors/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // 패스워드 인코딩, 회원 저장
        return new BCryptPasswordEncoder();
    }
}
