package org.bcliu.pojo;

import lombok.Data;
import org.bcliu.enumType.UserType;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class User {
    private BigInteger id;
    private UserType userType;
    private String phoneNumber;
    private String nickname;
    private String avatarUrl;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
