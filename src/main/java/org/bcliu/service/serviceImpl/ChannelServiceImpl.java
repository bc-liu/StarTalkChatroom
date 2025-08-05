package org.bcliu.service.serviceImpl;

import org.bcliu.dto.ChannelDTO;
import org.bcliu.mapper.ChannelMapper;
import org.bcliu.pojo.Channel;
import org.bcliu.service.ChannelService;
import org.bcliu.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    private ChannelMapper channelMapper;

    @Override
    public void create(ChannelDTO channelDTO) {
        //从DTO解析数据
        String name = channelDTO.getName();
        boolean isPublic = channelDTO.getIsPublic();
        //获取创建者id
        Map<String, Object> map = ThreadLocalUtil.get();
        Object idObj = map.get("id");

        if(idObj instanceof Number numId){
            BigInteger creatorId = BigInteger.valueOf(numId.longValue());
            //构建Channel对象
            Channel channel = Channel.builder()
                    .name(name)
                    .creatorId(creatorId)
                    .isPublic(channelDTO.getIsPublic())
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            //存到数据库
            channelMapper.create(channel);
        }else throw new RuntimeException("ChannelServiceImpl internal error: 数字类型不匹配");

    }
}
