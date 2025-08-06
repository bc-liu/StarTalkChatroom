package org.bcliu.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bcliu.pojo.ChannelMember;
import org.bcliu.pojo.User;

@Mapper
public interface ChannelMemberMapper {

    @Insert("insert into channel_members(channel_id,user_id,role) values (#{channelId},#{userId},#{role})")
    void join(ChannelMember channelMember);

    @Select("select * from channel_members where channel_id=#{channelId} and user_id=#{userId}")
    ChannelMember find(Long channelId, Long userId);

    @Delete("delete from channel_members where channel_id=#{channelId} and user_id=#{userId}")
    void leave(ChannelMember channelMember);
}
