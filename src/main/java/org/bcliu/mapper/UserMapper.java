package org.bcliu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bcliu.enumType.UserType;
import org.bcliu.pojo.User;

@Mapper
public interface UserMapper {
    @Select("select * from users where phone_number=#{phoneNumber}")
    User findByPhoneNumber(String phoneNumber);

    @Insert("insert into users(user_type,phone_number,nickname) values (#{userType},#{phoneNumber},#{nickname})")
    void add(User user);
}
