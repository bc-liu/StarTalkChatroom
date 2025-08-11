package org.bcliu.dto;

import lombok.Data;
import org.bcliu.enumType.Role;
import org.bcliu.enumType.UserType;

import java.time.LocalDateTime;

@Data
public class ChannelMemberDetailDTO {
    private Long userId;
    private UserType userType;
    private String nickname;
    private String avatarUrl;
    private Role role;
    private Boolean isMuted;
    private LocalDateTime createTime;
}
