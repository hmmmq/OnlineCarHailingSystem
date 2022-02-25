package com.foodie.loginprovider.service;

import com.foodie.dto.UserD;
import com.foodie.loginprovider.mapper.UserMapper;
import com.foodie.pojo.User;
import com.foodie.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserMapper userMapper;



    @Override
    public UserD findUser(User user) {
        User user1 = userMapper.selectUserByPrimaryKey(user);
        if(user1!=null){
            UserD userD=new UserD();
            userD.setId(user1.getid());
            userD.setPassword(user1.getPassword());
            userD.setName(user1.getName());
            userD.setPhone(user1.getPhone());
            userD.setType(user1.getType());
            return userD;
        }
        else{
            return new UserD(0,null,null,null,0);
        }

    }
}
