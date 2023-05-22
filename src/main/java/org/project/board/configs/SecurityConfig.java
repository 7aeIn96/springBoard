package org.project.board.configs;

import jakarta.servlet.http.HttpServletResponse;
import org.project.board.models.member.LoginFailureHandler;
import org.project.board.models.member.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 설정 클래스
public class SecurityConfig {
    @Bean // 스프링 관리 객체
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // 로그인, 로그아웃 url 구성
        http.formLogin()
                .loginPage("/member/login")
                .usernameParameter("userId")
                .passwordParameter("userPw")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                .and()

                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/member/login");

        // URL 패턴에 따라 접근할 수 있는 인가
        http.authorizeHttpRequests()
                .requestMatchers("/mypage/**").authenticated() // 회원 전용
//                .requestMatchers("/admin/**").hasAuthority("ADMIN") // 관리자 전용
                .anyRequest().permitAll(); // 그 외 모든 페이지는 모든 회원 접근 가능

        http.exceptionHandling() // url과 Role에 따라서 권한이 다르면 Exception 불러오기
                .authenticationEntryPoint((req, res, e) -> {
                    String URI = req.getRequestURI();

                    if (URI.indexOf("/admin") != -1) { // 관리자 페이지
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT AUTHORIZED");
                    } else { // 회원 전용 페이지
                        String redirectURL = req.getContextPath() + "/member/login"; // 일반 방식 = 회원 로그인
                        res.sendRedirect(redirectURL);
                    }
                });

        http.headers().frameOptions().sameOrigin(); // 같은 도메인인 경우 Iframe 데이터 공유

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
