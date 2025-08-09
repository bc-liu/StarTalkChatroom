package org.bcliu.mapper;

import org.apache.ibatis.annotations.*;
import org.bcliu.dto.MuteRequestDTO;
import org.bcliu.pojo.ChannelMember;
import org.bcliu.pojo.User;

import java.time.LocalDateTime;

@Mapper
public interface ChannelMemberMapper {

    @Insert("insert into channel_members(channel_id,user_id,role) values (#{channelId},#{userId},#{role})")
    void join(ChannelMember channelMember);

    @Select("select * from channel_members where channel_id=#{channelId} and user_id=#{userId}")
    ChannelMember find(Long channelId, Long userId);

    @Delete("delete from channel_members where channel_id=#{channelId} and user_id=#{userId}")
    void leave(ChannelMember channelMember);

    @Update("update channel_members set is_muted=true,muted_until=#{mutedUntil} where channel_id=#{targetMember.channelId} and user_id=#{targetMember.userId}")
    void mute(ChannelMember targetMember, LocalDateTime mutedUntil);

    @Update("update channel_members set is_muted=false where channel_id=#{channelId} and user_id=#{userId}")
    void dismute(ChannelMember targetMember);

    @Update("update channel_members set role=#{role} where channel_id=#{channelId} and user_id=#{userId}")
    void setAdmin(ChannelMember targetMember);

    @Update("update channel_members set role=#{role} where channel_id=#{channelId} and user_id=#{userId}")
    void disAdmin(ChannelMember targetMember);
}
