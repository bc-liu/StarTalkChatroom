package org.bcliu.service;

import org.bcliu.dto.RegisterDTO;
import org.bcliu.pojo.User;

public interface AuthService {
    User findByPhoneNumber(String phoneNumber);

    void register(RegisterDTO registerDTO);

    void login(RegisterDTO registerDTO);

    void logout(String token);

}
