package com.foodie.loginprovider.mapper;

import com.foodie.dto.UserD;
import com.foodie.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectUserByPrimaryKey(User userD);
    int insertUser(User record);


}
