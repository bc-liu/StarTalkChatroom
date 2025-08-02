package org.bcliu.service.serviceImpl;

import org.bcliu.dto.RegisterDTO;
import org.bcliu.enumType.UserType;
import org.bcliu.mapper.UserMapper;
import org.bcliu.pojo.User;
import org.bcliu.service.UserService;
import org.bcliu.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VerificationService verificationService;

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
}
