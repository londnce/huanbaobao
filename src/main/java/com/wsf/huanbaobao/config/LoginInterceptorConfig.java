package com.wsf.huanbaobao.config;

import com.wsf.huanbaobao.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 处理器拦截器的注册
 */
@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer {

    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建自定义的拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();

        //完成拦截器的注册
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                //放行静态资源
                .excludePathPatterns("/admin/login","/web/login.html","/web/index.html",
                        "/web/register.html","/web/product.html",
                        "/web/components/**","/web/search.html",
                        "/css/**","/bootstrap3/**", "/images/**","/js/**")
                //放行请求接口和支付宝沙箱接口
                .excludePathPatterns("/user/**","/address/**","/district/**",
                        "/product/**","/cart/**","/order/**","/kaptcha/**",
                         "/admin/**","/favorites/**","/alipay/**")
                //不放行/error页面有可能导致白名单失效假象
                .excludePathPatterns("/error");
    }
}
