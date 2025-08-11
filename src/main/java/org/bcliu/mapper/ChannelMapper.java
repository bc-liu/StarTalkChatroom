package org.bcliu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.bcliu.dto.ChannelDetailDTO;
import org.bcliu.dto.ChannelMemberDetailDTO;
import org.bcliu.dto.MessageDetailDTO;
import org.bcliu.pojo.Channel;

import java.util.List;

@Mapper
public interface ChannelMapper {

    @Insert("insert into channels(name,creator_id,is_public) values (#{name},#{creatorId},#{isPublic})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(Channel channel);

    @Select("select * from channels where is_public=1 order by update_time desc")
    List<Channel> findPublicChannels();

    @Select("select * from channels where id=#{channelId}")
    Channel findById(Long channelId);

    List<Channel> findJoinedChannelsByUserId(Long userId);

    List<ChannelMemberDetailDTO> getChannelMemberDetails(Long channelId);

    List<MessageDetailDTO> getMessageDetails(Long channelId);
}
