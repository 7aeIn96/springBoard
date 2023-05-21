package org.project.board.configs.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 사이트 설정 유지 인터셉터 ( 공통 기능 처리 )
 *      ( + 버전 관리 )
 */

@Component // 필수!
public class SiteConfigInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청 처리 전, 컨트롤러 빈 실행 전 호출 / 공통 기능, 버전 미리 정의
        request.setAttribute("cssJsVersion", 1);
        return true; // 통제 제어
    }
}
