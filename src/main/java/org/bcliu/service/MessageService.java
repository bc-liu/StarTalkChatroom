package org.bcliu.service;

import org.bcliu.dto.MessageDTO;
import org.bcliu.dto.MessageDetailDTO;
import org.bcliu.pojo.Message;
import org.bcliu.pojo.PageBean;
import org.springframework.stereotype.Service;

public interface MessageService {
    void send(Long channelId, Long senderId, MessageDTO messageDTO);

    PageBean<MessageDetailDTO> history(Long channelId, Long userId, Integer pageNum, Integer pageSize);
}
