package com.foodie.loginprovider.service;

import com.foodie.dto.ServerResponse;
import com.foodie.dto.UserD;
import com.foodie.loginprovider.mapper.UserMapper;
import com.foodie.pojo.User;
import com.foodie.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {


    @Autowired
    UserMapper userMapper;


    @Override
    public ServerResponse registerUser(UserD userD) {
        User user=new User(0,userD.getPassword(),userD.getName(),userD.getPhone(),userD.getType());
        int ires = userMapper.insertUser(user);
        if (ires != 0) {
            return ServerResponse.createBySuccess("register success: 影响行数" + ires, user);
        }
        else {return ServerResponse.createByErrorMessage("register fail");}
    }


}
