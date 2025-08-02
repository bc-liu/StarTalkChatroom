package org.bcliu.pojo;

import lombok.Data;
import org.bcliu.enumType.ContentType;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class Message {
    private BigInteger id;
    private BigInteger channelId;
    private BigInteger senderId;
    private ContentType contentType;
    private String content;
    private String metadata;
    private LocalDateTime create_time;
}
