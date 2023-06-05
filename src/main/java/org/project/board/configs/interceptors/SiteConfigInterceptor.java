package org.project.board.configs.interceptors;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.project.board.commons.configs.ConfigInfoService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * 사이트 설정 유지 인터셉터 ( 공통 기능 처리 )
 *      ( + 버전 관리 )
 */

@Component("siteConf") // 필수!
@RequiredArgsConstructor
public class SiteConfigInterceptor implements HandlerInterceptor {
    private final ConfigInfoService infoService;
    private final HttpServletRequest request;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청 처리 전, 컨트롤러 빈 실행 전 호출 / 공통 기능, 버전 미리 정의

        /** 사이트 설정 조회 */
        Map<String, String> siteConfigs = infoService.get("siteConfig",  new TypeReference<Map<String, String>>() {});
        request.setAttribute("siteConfig", siteConfigs);

        return true;
    }
    public String get(String name) { // SiteConfig 값이 있으면 가져오고, null이면 기본값

        Map<String, String> siteConfig = (Map<String, String>) request.getAttribute("siteConfig");
        String value = siteConfig == null ? "" : siteConfig.get(name);

        return value;
    }
}
