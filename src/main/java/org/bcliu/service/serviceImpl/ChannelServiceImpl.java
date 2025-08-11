package org.bcliu.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.bcliu.dto.ChannelMemberDetailDTO;
import org.bcliu.dto.ChannelDTO;
import org.bcliu.dto.ChannelDetailDTO;
import org.bcliu.dto.MessageDetailDTO;
import org.bcliu.enumType.Role;
import org.bcliu.mapper.ChannelMapper;
import org.bcliu.mapper.ChannelMemberMapper;
import org.bcliu.mapper.UserMapper;
import org.bcliu.pojo.*;
import org.bcliu.service.ChannelService;
import org.bcliu.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private ChannelMemberMapper channelMemberMapper;
    @Autowired
    private UserMapper userMapper;

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

    @Override
    public PageBean<Channel> getJoinedChannels(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> map = ThreadLocalUtil.get();
        Object idObj = map.get("id");
        Long id = ((Number) idObj).longValue();

        List<Channel> joinedChannels = channelMapper.findJoinedChannelsByUserId(id);

        Page<Channel> page = (Page<Channel>) joinedChannels;

        return new PageBean<>(page.getTotal(), page.getResult());
    }

    @Override
    public ChannelDetailDTO details(Long channelId, Integer pageNum, Integer pageSize) {
        //如果操作者没有加入该频道，则无法获得信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Object idObj = map.get("id");
        Long id = ((Number) idObj).longValue();

        if(channelMemberMapper.find(channelId, id) == null){
            throw new RuntimeException("您还不是该频道成员，无法获取频道详情信息");
        }

        //构建DTO
        //获取频道对象
        Channel channel = channelMapper.findById(channelId);
        //获取创建者对象
        User creator = userMapper.findById(channel.getCreatorId());
        //构建creator-SimpleUserDTO
        ChannelDetailDTO.SimpleUserDTO creatorDTO = ChannelDetailDTO.SimpleUserDTO.builder()
                .id(channel.getCreatorId().longValue())
                .avatarUrl(creator.getAvatarUrl())
                .nickname(creator.getNickname())
                .build();
        //数据库查找，获取channelMemberDetailDTO
        List<ChannelMemberDetailDTO> channelMembersDetailList = channelMapper.getChannelMemberDetails(channelId);

        //流式构建members
        List<ChannelDetailDTO.ChannelMemberDTO> members = channelMembersDetailList.stream()
                .map(detail -> {
                    ChannelDetailDTO.SimpleUserDTO userDTO = ChannelDetailDTO.SimpleUserDTO.builder()
                            .id(detail.getUserId())
                            .nickname(detail.getNickname())
                            .avatarUrl(detail.getAvatarUrl())
                            .build();

                    return ChannelDetailDTO.ChannelMemberDTO.builder()
                            .user(userDTO)
                            .role(detail.getRole())
                            .isMuted(detail.getIsMuted())
                            .createTime(detail.getCreateTime())
                            .build();
                })
                .collect(Collectors.toList());

        PageHelper.startPage(pageNum, pageSize);
        //数据库查找消息
        List<MessageDetailDTO> messageDetails = channelMapper.getMessageDetails(channelId);
        //流式构建消息
        List<ChannelDetailDTO.SimpleMessageDTO> recentMessages = messageDetails.stream()
                .map(detail -> {
                    ChannelDetailDTO.SimpleUserDTO userDTO = ChannelDetailDTO.SimpleUserDTO.builder()
                            .id(detail.getSenderId())
                            .nickname(detail.getSenderNickname())
                            .avatarUrl(detail.getSenderAvatarUrl())
                            .build();

                    return ChannelDetailDTO.SimpleMessageDTO.builder()
                            .id(detail.getSenderId())
                            .sender(userDTO)
                            .contentType(detail.getContentType())
                            .content(detail.getContent())
                            .metadata(detail.getMetadata())
                            .createTime(detail.getCreateTime())
                            .build();
                }).collect(Collectors.toList());

        //构建最终的channelDetailDTO
        ChannelDetailDTO channelDetailDTO = ChannelDetailDTO.builder()
                .id(channelId)
                .name(channel.getName())
                .creator(creatorDTO)
                .members(members)
                .recentMessages(recentMessages)
                .build();

        return channelDetailDTO;
    }
}
