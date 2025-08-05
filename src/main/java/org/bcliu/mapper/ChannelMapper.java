package org.bcliu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.bcliu.pojo.Channel;

@Mapper
public interface ChannelMapper {

    @Insert("insert into channels(name,creator_id,is_public) values (#{name},#{creatorId},#{isPublic})")
    void create(Channel channel);
}
