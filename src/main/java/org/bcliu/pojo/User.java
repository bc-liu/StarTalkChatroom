package org.bcliu.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bcliu.enumType.UserType;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private BigInteger id;
    private UserType userType;
    private String phoneNumber;
    private String nickname;
    private String avatarUrl;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
