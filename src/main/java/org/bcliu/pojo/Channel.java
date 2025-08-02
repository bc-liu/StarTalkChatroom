package org.bcliu.pojo;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class Channel {
    private BigInteger id;
    private String name;
    private BigInteger creatorId;
    private Boolean isPublic;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
