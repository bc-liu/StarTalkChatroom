package org.bcliu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bcliu.pojo.ChannelMember;

@Mapper
public interface ChannelMemberMapper {
    static void add(ChannelMember member) {
    }
}
