package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    public WebAppConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(loginInterceptor).
                addPathPatterns(
                        "/receives/search",
                        "/receives/export",
                        //"/signUp/search",
                        "/signUp/export");

    }
}