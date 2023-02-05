package com.wsf.huanbaobao.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wsf.huanbaobao.entity.Admin;
import com.wsf.huanbaobao.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.rmi.ServerException;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServerException("无token，请重新登录");
        }
        // 获取 token 中的 admin id
        String adminId;
        try {
            adminId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("token验证失败，请重新登录");
        }
        //根据token中的id查询数据库
        QueryWrapper<Admin> qw = new QueryWrapper<>();
        qw.eq("id",adminId);
        Admin admin = adminService.getOne(qw);
        if (admin == null) {
            throw new ServerException("用户不存在，请重新登录");
        }
        // 管理员密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getAdminPw())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServerException("token验证失败，请重新登录");
        }
        return true;
    }
}
