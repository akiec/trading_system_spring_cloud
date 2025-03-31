package com.onlineshop.config;

import com.onlineshop.Utils.LoginInterceptor;
import com.onlineshop.Utils.RefreshInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//拦截器配置类
public class MVCConfig implements WebMvcConfigurer {
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns("/user/login","/user/loginByPhone","/user/code").order(1);
        registry.addInterceptor(new RefreshInterceptor()).addPathPatterns("/**").order(0);
    }
}
