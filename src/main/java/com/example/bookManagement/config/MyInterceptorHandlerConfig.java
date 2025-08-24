package com.example.bookManagement.config;

import com.example.bookManagement.interceptor.MyInterceptorHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MyInterceptorHandlerConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptorHandler())
                .addPathPatterns("/**")
                .excludePathPatterns(" ");
//        /** match all pattern
//        /api/* -> /api/a  -- but not /api/a/b
    }
}
