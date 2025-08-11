package org.bcliu.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageDetailDTO {
    private Long id;
    private String contentType;
    private String content;
    private String metadata;
    private LocalDateTime createTime;

    private Long senderId;
    private String senderNickname;
    private String senderAvatarUrl;
}
