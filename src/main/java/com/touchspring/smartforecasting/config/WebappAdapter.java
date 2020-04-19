package com.touchspring.smartforecasting.config;

import com.touchspring.smartforecasting.interceptor.LoginInterceptor;
import com.touchspring.smartforecasting.service.SysLogService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebappAdapter implements WebMvcConfigurer {

    private final SysLogService sysLogService;

    private static List<String> EXCLUDE_PATHS = new ArrayList<>();

    {
        EXCLUDE_PATHS.add("/login");
        EXCLUDE_PATHS.add("/dictionaries");
        EXCLUDE_PATHS.add("/languages");
        EXCLUDE_PATHS.add("/auto-login");
        EXCLUDE_PATHS.add("/mock/**");
        EXCLUDE_PATHS.add("/file/**");
        EXCLUDE_PATHS.add("/error");
        EXCLUDE_PATHS.add("/register");
        EXCLUDE_PATHS.add("/templates/**");
        EXCLUDE_PATHS.add("/export-users");
        EXCLUDE_PATHS.add("/export-suppliers");
        EXCLUDE_PATHS.add("/export-sysLogs");
        EXCLUDE_PATHS.add("/export-toolings");
        EXCLUDE_PATHS.add("/reset-password");
        EXCLUDE_PATHS.add("/forget-password");
        EXCLUDE_PATHS.add("/check-sign");
        EXCLUDE_PATHS.add("/toolings-template");
        EXCLUDE_PATHS.add("/user-template");
        EXCLUDE_PATHS.add("/supplier-template");
        EXCLUDE_PATHS.add("/register/mail");
        EXCLUDE_PATHS.add("/verify-code");
    }

    public WebappAdapter(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    /**
     * 注册拦截器s
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 添加拦截url，     excludePathPatterns 排除拦截url
        registry.addInterceptor(new LoginInterceptor(sysLogService)).addPathPatterns("/**").excludePathPatterns(EXCLUDE_PATHS);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
