package org.bcliu.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final String KEY = "bcliu";

    //接受业务数据，返回生成token
    public static String genToken(Map<String, Object> claims){
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*60*24)) //1day有效期
                .sign(Algorithm.HMAC256(KEY));
    }

    //接收token，验证token，返回业务数据
    public static Map<String, Object> parseToken(String token){
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }
}
