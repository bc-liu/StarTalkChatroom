package org.bcliu.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bcliu.pojo.ChannelMember;
import org.bcliu.pojo.User;

@Mapper
public interface ChannelMemberMapper {

    @Insert("insert into channel_members(channel_id,user_id) values (#{channelId},#{userId})")
    void join(ChannelMember channelMember);

    @Select("select * from channel_members where user_id=#{uid}")
    ChannelMember findByUid(Long uid);

    @Delete("delete from channel_members where user_id=#{uid}")
    void leave(Long uid);
}
