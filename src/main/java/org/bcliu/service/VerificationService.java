package org.bcliu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String SMS_CODE_PREFIX = "sms:code:";
    private static final int CODE_EXPIRATION_MINUTES = 5;//有效期 分
    private static final int CODE_LENGTH = 4;//验证码长度

    //生成验证码
    public String genAndStoreCode(String phoneNumber){
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(RANDOM.nextInt(10));
        }

        //存入redis
        String redisKey = SMS_CODE_PREFIX + phoneNumber;
        redisTemplate.opsForValue().set(redisKey, code.toString(), CODE_EXPIRATION_MINUTES, TimeUnit.MINUTES);
        return code.toString();
    }

    //验证码校验
    public boolean verifyCode(String phoneNumber, String submittedCode){
        String redisKey = SMS_CODE_PREFIX + phoneNumber;
        String correctCode = redisTemplate.opsForValue().get(redisKey);

        //检查是否存在或过期
        if(correctCode == null) return false;
        //检查是否正确
        if(correctCode.equals(submittedCode)){
            redisTemplate.delete(redisKey);
            return true;
        }
        return false;
    }
}
