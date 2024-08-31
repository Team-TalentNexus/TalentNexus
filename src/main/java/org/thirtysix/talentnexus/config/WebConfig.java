package org.thirtysix.talentnexus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thirtysix.talentnexus.controller.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/seeker/**") // 需要保护的路径
                .excludePathPatterns("/seeker/login", "/seeker/register"); // 不需要拦截的路径
    }
}

