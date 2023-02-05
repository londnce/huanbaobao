package com.wsf.huanbaobao.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 检测全局session对象中是否有uid数据，如果有则放行，如果没有重定向到登录页面
     * @param request   请求对象
     * @param response  响应对象
     * @param handler   处理器（url+controller:映射）
     * @return  如果返回值为true表示放行当前请求，如果返回值为false则表示拦截当前请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取项目工程的session
        HttpSession session = request.getSession();
        if (session.getAttribute("uid") != null){ //说明此时已登录
            return true;
        }else{ //说明未登录
            //重定向至登录页面
            response.sendRedirect("/web/login.html");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
