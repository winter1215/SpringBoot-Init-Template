package com.winter.flower.config;

import com.winter.flower.config.security.SecurityConfiguration;
import com.winter.flower.interceptor.TokenHandlerInterceptor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * WebMvc配置 : 静态文件、拦截器等
 * @author winter
 * @create 2024-01-15 17:06
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final TokenHandlerInterceptor tokenHandlerInterceptor;
    private final SecurityConfiguration securityConfiguration;
    // 拦截器添加
    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        registry.addInterceptor(tokenHandlerInterceptor)
                // 不包括 context-path
                .excludePathPatterns(securityConfiguration.getWhiteList());
    }
}