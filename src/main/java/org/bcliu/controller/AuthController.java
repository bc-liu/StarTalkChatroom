package org.bcliu.controller;

import org.bcliu.dto.RegisterDTO;
import org.bcliu.pojo.Result;
import org.bcliu.pojo.User;
import org.bcliu.service.UserService;
import org.bcliu.service.VerificationService;
import org.bcliu.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private VerificationService verificationService;

    @PostMapping("/sendCode")
    public Result sendCode(@RequestBody Map<String, String> payload){
        String phoneNumber = payload.get("phoneNumber");

        //生成验证码
        String code = verificationService.genAndStoreCode(phoneNumber);

        //发送验证码
        String content = "【星语聊天室】您的验证码是：" + code + "，5分钟内有效";
        SmsUtil.send(phoneNumber, content);

        return Result.success();
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO registerDTO){
        try {
            userService.register(registerDTO);
            return Result.success();
        }catch (RuntimeException e){
            return Result.error(e.getMessage());
        }
    }
}
