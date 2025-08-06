package org.bcliu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.bcliu.pojo.ChannelMember;

@Mapper
public interface ChannelMemberMapper {

    @Insert("insert into channel_members(channel_id,user_id) values (#{channelId},#{userId})")
    void join(ChannelMember channelMember);
}
