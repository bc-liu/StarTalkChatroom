package org.bcliu.dto;

import lombok.Data;
import org.bcliu.enumType.ContentType;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private ContentType contentType;
    private String content;
    private String metadata;
}
