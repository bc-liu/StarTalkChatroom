package org.bcliu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bcliu.pojo.Channel;

import java.util.List;

@Mapper
public interface ChannelMapper {

    @Insert("insert into channels(name,creator_id,is_public) values (#{name},#{creatorId},#{isPublic})")
    void create(Channel channel);

    @Select("select * from channels where is_public=1 order by update_time desc")
    List<Channel> findPublicChannels();

    @Select("select * from channels where id=#{channelId};")
    Channel findById(Long channelId);
}
