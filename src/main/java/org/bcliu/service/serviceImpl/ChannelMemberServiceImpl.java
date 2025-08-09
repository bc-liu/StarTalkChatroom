package org.bcliu.service.serviceImpl;

import org.bcliu.dto.MuteRequestDTO;
import org.bcliu.enumType.Role;
import org.bcliu.mapper.ChannelMapper;
import org.bcliu.mapper.ChannelMemberMapper;
import org.bcliu.pojo.Channel;
import org.bcliu.pojo.ChannelMember;
import org.bcliu.service.ChannelMemberService;
import org.bcliu.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
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
                .role(Role.member)
                .build();
        //该用户已加入频道，则拒绝重复加入
        if(channelMemberMapper.find(channelId, uid) != null){
            throw new RuntimeException("请勿重复加入频道");
        }
        //添加到数据库
        channelMemberMapper.join(channelMember);
    }

    @Override
    public void leave(Long channelId) {
        //根据id获取频道
        Channel channel = channelMapper.findById(channelId);
        //获取当前用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Object uidObj = map.get("id");
        Long uid = ((Number) uidObj).longValue();
        //用户不在当前频道,拒绝删除
        if(channelMemberMapper.find(channelId, uid) == null){
            throw new RuntimeException("您不是该频道成员");
        }
        //删除数据库中成员
        ChannelMember channelMember = ChannelMember.builder()
                .channelId(BigInteger.valueOf(channelId))
                .userId(BigInteger.valueOf(uid))
                .build();
        channelMemberMapper.leave(channelMember);
    }

    @Override
    public void kick(Long channelId, Long userId) {
        //根据id获取频道
        Channel channel = channelMapper.findById(channelId);
        //获取当前操作用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Object opsObj = map.get("id");
        Long opsId = ((Number) opsObj).longValue();

        //检查管理员或创建者权限，如没有则操作失败
        ChannelMember operator = channelMemberMapper.find(channelId, opsId);
        if(operator == null){//操作者非空判断
            throw new RuntimeException("非本频道成员不能进行此操作");
        }
        if(operator.getRole() != Role.admin && operator.getRole() != Role.creator){
            throw new RuntimeException("您不具备踢出该频道成员的权限");
        }
        //拟踢成员对象
        ChannelMember targetMember = channelMemberMapper.find(channelId, userId);
        //该成员不存在，无法踢出
        if(targetMember == null){
            throw new RuntimeException("该成员不存在");
        }
        //无法踢出自己
        if(operator.getId().longValue() == targetMember.getId().longValue()){
            throw new RuntimeException("无法进行该操作");
        }
        //管理员无法踢出管理员和创建者
        if(operator.getRole() == Role.admin && (targetMember.getRole() == Role.creator || targetMember.getRole() == Role.admin)){
            throw new RuntimeException("您不具备踢出该频道成员的权限");
        }
        //从数据库删除被踢成员
        channelMemberMapper.leave(targetMember);
    }

    @Override
    public void mute(Long channelId, Long operatorId, Long userId, MuteRequestDTO muteRequestDTO) {
        //无管理员或创建者权限，则操作失败
        ChannelMember operator = channelMemberMapper.find(channelId, operatorId);
        if(operator == null){//操作者非空判断
            throw new RuntimeException("非本频道成员不能进行此操作");
        }
        if(operator.getRole() != Role.admin && operator.getRole() != Role.creator){
            throw new RuntimeException("您不具备禁言该频道成员的权限");
        }
        //拟禁言成员对象
        ChannelMember targetMember = channelMemberMapper.find(channelId, userId);
        //该成员不存在，无法禁言
        if(targetMember == null){
            throw new RuntimeException("该成员不存在");
        }
        //无法禁言自己
        if(operator.getId().longValue() == targetMember.getId().longValue()){
            throw new RuntimeException("无法进行该操作");
        }
        //管理员无法禁言管理员和创建者
        if(operator.getRole() == Role.admin && (targetMember.getRole() == Role.creator || targetMember.getRole() == Role.admin)){
            throw new RuntimeException("您不具备禁言该频道成员的权限");
        }
        //计算禁言结束时间
        LocalDateTime mutedUntil = LocalDateTime.now().plusMinutes(muteRequestDTO.getDurationInMinutes());
        //操作数据库
        channelMemberMapper.mute(targetMember, mutedUntil);
    }

    @Override
    public void dismute(Long channelId, Long operatorId, Long userId) {
        //无管理员或创建者权限，则操作失败
        ChannelMember operator = channelMemberMapper.find(channelId, operatorId);
        if(operator == null){//操作者非空判断
            throw new RuntimeException("非本频道成员不能进行此操作");
        }
        if(operator.getRole() != Role.admin && operator.getRole() != Role.creator){
            throw new RuntimeException("您不具备解除禁言该频道成员的权限");
        }
        //拟解除禁言成员对象
        ChannelMember targetMember = channelMemberMapper.find(channelId, userId);
        //该成员不存在，无法解除禁言
        if(targetMember == null){
            throw new RuntimeException("该成员不存在");
        }
        //操作数据库
        channelMemberMapper.dismute(targetMember);
    }
}
