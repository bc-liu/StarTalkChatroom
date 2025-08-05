package org.bcliu.pojo;

import lombok.Builder;
import lombok.Data;
import org.bcliu.enumType.Role;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
public class ChannelMember {
    private BigInteger id;
    private BigInteger channelId;
    private BigInteger userId;
    private Role role;
    private Boolean isMuted;
    private LocalDateTime mutedUntil;
    private LocalDateTime createTime;
}
