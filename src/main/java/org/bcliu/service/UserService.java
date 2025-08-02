package org.bcliu.service;

import org.bcliu.dto.RegisterDTO;
import org.bcliu.enumType.UserType;
import org.bcliu.pojo.User;

public interface UserService {
    User findByPhoneNumber(String phoneNumber);

    void register(RegisterDTO registerDTO);
}
