package org.bcliu.dto;

import lombok.Builder;
import lombok.Data;
import org.bcliu.enumType.Role;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ChannelDetailDTO {
    private Long id;
    private String name;
    private SimpleUserDTO creator;
    private List<ChannelMemberDTO> members;
    private List<SimpleMessageDTO> recentMessages;

    @Data
    @Builder
    public static class SimpleUserDTO{
        private Long id;
        private String nickname;
        private String avatarUrl;
    }

    @Data
    @Builder
    public static class ChannelMemberDTO{
        private SimpleUserDTO user;
        private Role role;
        private Boolean isMuted;
        private LocalDateTime createTime;
    }

    @Data
    @Builder
    public static class SimpleMessageDTO{
        private Long id;
        private SimpleUserDTO sender;
        private String contentType;
        private String content;
        private String metadata;
        private LocalDateTime createTime;
    }
}
