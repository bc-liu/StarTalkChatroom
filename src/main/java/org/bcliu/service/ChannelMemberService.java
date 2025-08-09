package org.bcliu.service;

import org.bcliu.dto.MuteRequestDTO;

public interface ChannelMemberService {
    void join(Long channelId);

    void leave(Long channelId);

    void kick(Long channelId, Long userId);

    void mute(Long channelId, Long operatorId, Long userId, MuteRequestDTO muteRequestDTO);

    void dismute(Long channelId, Long operatorId, Long userId);
}
