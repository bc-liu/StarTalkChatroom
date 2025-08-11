package org.bcliu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.bcliu.enumType.UserType;
import org.bcliu.pojo.User;

import java.math.BigInteger;

@Mapper
public interface UserMapper {
    @Select("select * from users where phone_number=#{phoneNumber}")
    User findByPhoneNumber(String phoneNumber);

    @Insert("insert into users(user_type,phone_number,nickname) values (#{userType},#{phoneNumber},#{nickname})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(User user);

    @Select("select * from users where id=#{id}")
    User findById(BigInteger id);
}
