package org.project.board.configs;

import lombok.RequiredArgsConstructor;
import org.project.board.configs.interceptors.SiteConfigInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing // 프록시 자동 생성, 소스 추가
public class MvcConfig implements WebMvcConfigurer { // // 스프링에 내장되어 있는 WebMvc 재설정

    @Value("${file.upload.path}")
    private String fileUploadPath;

    // 사이트 설정 유지 인터셉터
    private final SiteConfigInterceptor siteConfigInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) { // 템플릿 구성 후 변경할 예정 ( 임시 URL 연동 )
        registry.addViewController("/")
                .setViewName("main/index");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { // 정적 경로 설정 ( 주로 파일 링크 연결 - file.upload.path )
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///" + fileUploadPath);
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) { // 기본 설정
        registry.addInterceptor(siteConfigInterceptor)
                .addPathPatterns("/**"); // 전체 ( 공통으로 유지하기때문에 )
    }
    @Bean
    public MessageSource messageSource() { // 메세지 번들 설정
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8");
        ms.setBasenames("messages.commons", "messages.validations", "messages.errors");

        return ms;
    }
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() { // GET, POST 외에 DELETE, PATCH, PUT... 사용 가능
        return new HiddenHttpMethodFilter();
    }
}
