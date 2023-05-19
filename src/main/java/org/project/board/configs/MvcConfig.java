package org.project.board.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer { // // 스프링에 내장되어 있는 WebMvc 재설정

    @Value("${file.upload.path}")
    private String fileUploadPath;

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
}
