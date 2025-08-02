package org.bcliu.pojo;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class Bot {
    private BigInteger id;
    private BigInteger botUserId;
    private String token;
    private LocalDateTime createTime;
}
