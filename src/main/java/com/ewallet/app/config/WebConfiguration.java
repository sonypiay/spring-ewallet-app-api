package com.ewallet.app.config;

import com.ewallet.app.middleware.ValidateAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private ValidateAccessToken validateAccessToken;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validateAccessToken).excludePathPatterns("/api/auth/*");
    }
}
