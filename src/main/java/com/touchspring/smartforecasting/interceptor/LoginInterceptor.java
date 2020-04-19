package com.touchspring.smartforecasting.interceptor;


import com.touchspring.smartforecasting.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private final SysLogService sysLogService;

    public LoginInterceptor(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        // 在请求处理之前进行调用（Controller方法调用之前）,返回true才会继续往下执行，返回false取消当前请求
//        String url = request.getRequestURI();
//        String tokenCode = request.getHeader("token");
//        UserAuthDTO user = JWTUtils.unSign(tokenCode, UserAuthDTO.class);
//        log.info("request :{}", url);
////        if (!StringUtils.equals(request.getMethod().toUpperCase(), "GET")) {
////            sysLogService.log("[" + request.getMethod() + "]" + url, user.getId(), null, null);
////        }
//        return true;
//    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}

