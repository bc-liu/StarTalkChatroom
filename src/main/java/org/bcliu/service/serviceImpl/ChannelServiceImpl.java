package org.bcliu.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.bcliu.dto.ChannelDTO;
import org.bcliu.enumType.Role;
import org.bcliu.mapper.ChannelMapper;
import org.bcliu.mapper.ChannelMemberMapper;
import org.bcliu.pojo.Channel;
import org.bcliu.pojo.ChannelMember;
import org.bcliu.pojo.PageBean;
import org.bcliu.service.ChannelService;
import org.bcliu.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private ChannelMemberMapper channelMemberMapper;

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

            //添加创建者到表channel_member,设置身份为creator
            ChannelMember channelMember = ChannelMember.builder()
                    .channelId(channel.getId())
                    .userId(BigInteger.valueOf(numId.longValue()))
                    .role(Role.creator)
                    .build();
            channelMemberMapper.join(channelMember);

        }else throw new RuntimeException("ChannelServiceImpl internal error: 数字类型不匹配");

    }

    @Override
    public PageBean<Channel> getPublicChannels(Integer pageNum, Integer pageSize) {
        PageBean<Channel> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<Channel> publicChannels = channelMapper.findPublicChannels();
        Page<Channel> page = (Page<Channel>) publicChannels;

        return new PageBean<>(page.getTotal(), page.getResult());
    }
}
