package org.bcliu.service.serviceImpl;

import org.bcliu.dto.RegisterDTO;
import org.bcliu.enumType.UserType;
import org.bcliu.mapper.UserMapper;
import org.bcliu.pojo.User;
import org.bcliu.service.UserService;
import org.bcliu.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        User u = userMapper.findByPhoneNumber(phoneNumber);
        return u;
    }

    @Override
    public void register(String phoneNumber) {
        //添加到数据库
        userMapper.add(phoneNumber, UserType.human);
    }
}
