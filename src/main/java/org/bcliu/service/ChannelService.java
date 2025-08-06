package org.bcliu.service;

import org.apache.ibatis.annotations.Select;
import org.bcliu.dto.ChannelDTO;
import org.bcliu.pojo.Channel;
import org.bcliu.pojo.PageBean;

public interface ChannelService {
    void create(ChannelDTO channelDTO);

    PageBean<Channel> getPublicChannels(Integer pageNum, Integer pageSize);
}
