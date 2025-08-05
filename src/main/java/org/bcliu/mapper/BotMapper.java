package org.bcliu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.bcliu.pojo.Bot;

@Mapper
public interface BotMapper {

    @Insert("insert into users()")
    void create(Bot bot);
}
