package org.bcliu.pojo;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
public class Bot {
    private BigInteger id;
    private BigInteger botUserId;
    private String token;
    private LocalDateTime createTime;
}
