package org.bcliu.service.serviceImpl;

import org.bcliu.enumType.UserType;
import org.bcliu.mapper.BotMapper;
import org.bcliu.mapper.ChannelMapper;
import org.bcliu.mapper.ChannelMemberMapper;
import org.bcliu.mapper.UserMapper;
import org.bcliu.pojo.Bot;
import org.bcliu.pojo.Channel;
import org.bcliu.pojo.ChannelMember;
import org.bcliu.pojo.User;
import org.bcliu.service.BotService;
import org.bcliu.service.ChannelMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.UUID;

@Service
public class BotServiceImpl implements BotService {
    @Autowired
    private BotMapper botMapper;
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void create(Long channelId, Long creatorId) {
        //权限校验
        //todo

        //创建机器人用户实体
        User botU = User.builder()
                .userType(UserType.bot)
                .nickname("小助手")
                .build();
        //添加
        userMapper.add(botU);

        //生成token
        String token = UUID.randomUUID().toString().replace("-", "");
        //创建机器人实体
        Bot bot = Bot.builder()
                .botUserId(botU.getId())
                .token(token)
                .build();
        //存入Bots表
        botMapper.create(bot);
        //机器人作为成员加入频道
        ChannelMember botMember = ChannelMember.builder()
                .channelId(BigInteger.valueOf(channelId))
                .userId(botU.getId())
                .build();

        ChannelMemberMapper.add(botMember);
    }
}
