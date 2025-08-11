package org.bcliu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.bcliu.dto.MessageDTO;
import org.bcliu.dto.MessageDetailDTO;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Insert("insert into messages(channel_id,sender_id,content_type,content,metadata,create_time) values (#{channelId},#{senderId},#{dto.contentType},#{dto.content},#{dto.metadata},now())")
    void send(Long channelId, Long senderId, @Param("dto") MessageDTO messageDTO);

    List<MessageDetailDTO> history(Long channelId);
}
