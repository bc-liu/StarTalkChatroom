package org.bcliu.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bcliu.utils.JwtUtil;
import org.bcliu.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头获取token
        String token = request.getHeader("Authorization");

        try {
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            //获取redis中token
            String redisToken = ops.get(token);
            //检验token是否正确
            if(redisToken == null){
                throw new RuntimeException("请先登录");
            }

            //token存入线程
            Map<String, Object> claims = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(claims);

            return true;
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }
}
