package org.bcliu.service.serviceImpl;

import org.bcliu.mapper.ChannelMapper;
import org.bcliu.mapper.ChannelMemberMapper;
import org.bcliu.pojo.Channel;
import org.bcliu.pojo.ChannelMember;
import org.bcliu.service.ChannelMemberService;
import org.bcliu.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;

@Service
public class ChannelMemberServiceImpl implements ChannelMemberService {
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private ChannelMemberMapper channelMemberMapper;

    @Override
    public void join(Long channelId) {
        //根据id获取频道
        Channel channel = channelMapper.findById(channelId);
        //频道非公开，无法加入
        boolean isPublic = channel.getIsPublic();
        if(!isPublic){
            throw new RuntimeException("无权限加入该频道");
        }
        //获取当前用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Object uidObj = map.get("id");
        Long uid = ((Number) uidObj).longValue();
        //创建频道成员对象
        ChannelMember channelMember = ChannelMember.builder()
                .channelId(BigInteger.valueOf(channelId))
                .userId(BigInteger.valueOf(uid))
                .build();
        //该用户已加入频道，则拒绝重复加入
        //todo
        //添加到数据库
        channelMemberMapper.join(channelMember);
    }
}
