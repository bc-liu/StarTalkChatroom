package org.bcliu.service;

import org.bcliu.dto.MessageDTO;
import org.springframework.stereotype.Service;

public interface MessageService {
    void send(Long channelId, Long senderId, MessageDTO messageDTO);
}
