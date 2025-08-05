package org.bcliu.service.serviceImpl;

import org.bcliu.dto.RegisterDTO;
import org.bcliu.enumType.UserType;
import org.bcliu.mapper.UserMapper;
import org.bcliu.pojo.User;
import org.bcliu.service.AuthService;
import org.bcliu.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VerificationService verificationService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        User u = userMapper.findByPhoneNumber(phoneNumber);
        return u;
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        //从DTO获取数据
        String phoneNumber = registerDTO.getPhoneNumber();
        String code = registerDTO.getCode();
        //检查用户是否存在
        User u = userMapper.findByPhoneNumber(phoneNumber);
        if(u != null){
            throw new RuntimeException("该手机号已注册");
        }
        //校验验证码
        if(!verificationService.verifyCode(phoneNumber, code)){
            throw new RuntimeException("验证码错误或失效");
        }
        //创建并保存用户
        User newU = new User();
        newU.setPhoneNumber(phoneNumber);
        newU.setUserType(UserType.human);
        newU.setNickname("星友" + phoneNumber.substring(phoneNumber.length()-4));
        //添加到数据库
        userMapper.add(newU);
    }

    @Override
    public void login(RegisterDTO registerDTO) {
        //从DTO解析数据
        String phoneNumber = registerDTO.getPhoneNumber();
        String code = registerDTO.getCode();
        //判断是否注册过
        User u = userMapper.findByPhoneNumber(phoneNumber);
        if(u == null){
            throw new RuntimeException("请先完成注册");
        }
        //校验验证码
        if(!verificationService.verifyCode(phoneNumber, code)){
            throw new RuntimeException("验证码错误或失效");
        }
        //登录
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", u.getId().longValue());
        claims.put("phoneNumber", u.getPhoneNumber());
        String token = JwtUtil.genToken(claims);
        //输出token
        System.out.println(token);

        //token存入redis,有效期1day
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(token, token, 1, TimeUnit.DAYS);
    }

    @Override
    public void logout(String token) {
        stringRedisTemplate.delete(token);
    }


}
