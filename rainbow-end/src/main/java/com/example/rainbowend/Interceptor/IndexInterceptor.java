package com.example.rainbowend.Interceptor;


import com.auth0.jwt.interfaces.Claim;
import com.example.rainbowend.Utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Rainbow
 * 对index请求设置拦截器
 *
 * @DATE:2023/8/8 0008
 */
@Component
public class IndexInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //获取token
        String token = request.getHeader("token");
        Map<String, Claim> verifyMap = JwtUtil.verifyToken(token);
        if (!verifyMap.isEmpty()) {
            System.out.println("校验成功");
            return true;
        } else {
            System.out.println("登录过期");
            return false;
        }
    }
}
