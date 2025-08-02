package org.bcliu.controller;

import org.bcliu.dto.RegisterDTO;
import org.bcliu.pojo.Result;
import org.bcliu.pojo.User;
import org.bcliu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(RegisterDTO registerDTO, String code){
        //查询用户
        User u = userService.findByPhoneNumber(registerDTO.getPhoneNumber());

        //如果查不到->注册
        if(u == null){
            //注册
            //验证码校验
            //todo
            userService.register(registerDTO);
            return Result.success();
        }else return Result.error("您的手机号已注册过账号，请直接登录");
    }
}
