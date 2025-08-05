package org.bcliu.pojo;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
public class Channel {
    private BigInteger id;
    private String name;
    private BigInteger creatorId;
    private Boolean isPublic;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
