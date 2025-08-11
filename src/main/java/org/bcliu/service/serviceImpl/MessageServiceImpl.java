package org.bcliu.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.bcliu.dto.MessageDTO;
import org.bcliu.dto.MessageDetailDTO;
import org.bcliu.mapper.ChannelMapper;
import org.bcliu.mapper.ChannelMemberMapper;
import org.bcliu.mapper.MessageMapper;
import org.bcliu.pojo.Channel;
import org.bcliu.pojo.ChannelMember;
import org.bcliu.pojo.Message;
import org.bcliu.pojo.PageBean;
import org.bcliu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private ChannelMemberMapper channelMemberMapper;

    private static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");

    @Override
    public void send(Long channelId, Long senderId, MessageDTO messageDTO) {
        //频道不存在，则不能发送
        Channel channel = channelMapper.findById(channelId);
        if(channel == null){
            throw new RuntimeException("该频道不存在");
        }
        //发送者不在频道，则不能发送
        ChannelMember sender = channelMemberMapper.find(channelId, senderId);
        if(sender == null){
            throw new RuntimeException("非频道成员不能发送消息");
        }
        //发送者被禁言且未到接触时间，则不能发送
        if(sender.getIsMuted() == true && LocalDateTime.now().isBefore(sender.getMutedUntil())){
            String formatTime = sender.getMutedUntil().format(CUSTOM_FORMATTER);
            throw new RuntimeException("您已被禁言，在" + formatTime + "前不能在该频道发送消息");
        }
        messageMapper.send(channelId, senderId, messageDTO);
    }

    @Override
    public PageBean<MessageDetailDTO> history(Long channelId, Long userId, Integer pageNum, Integer pageSize) {
        //频道不存在，则不能获取历史操作
        Channel channel = channelMapper.findById(channelId);
        if(channel == null){
            throw new RuntimeException("该频道不存在");
        }
        //用户不在频道，则不能获取
        ChannelMember sender = channelMemberMapper.find(channelId, userId);
        if(sender == null){
            throw new RuntimeException("非频道成员不能查看消息");
        }

        PageBean<Message> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<MessageDetailDTO> messages = messageMapper.history(channelId);
        Page<MessageDetailDTO> page = (Page<MessageDetailDTO>) messages;

        return new PageBean<>(page.getTotal(), page.getResult());
    }
}
