package org.bcliu.service;

public interface ChannelMemberService {
    void join(Long channelId);

    void leave(Long channelId);

    void kick(Long channelId, Long userId);
}
