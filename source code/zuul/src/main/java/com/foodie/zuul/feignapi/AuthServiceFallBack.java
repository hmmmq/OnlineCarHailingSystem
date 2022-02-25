package com.foodie.zuul.feignapi;

import com.foodie.dto.UserT;
import com.foodie.dto.UserTToken;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceFallBack implements AuthService{

    @Override
    public String createtoken(UserT userT) {
        return null;
    }

    @Override
    public Boolean refreshtoken(String key) {
        return null;
    }

    @Override
    public boolean refreshtoken(UserT userT) {
        return false;
    }

    @Override
    public UserT gettokenobject(String token) {
        return null;
    }

    @Override
    public Boolean checktokenbykey(String key, String token) {
        return null;
    }

    @Override
    public Boolean checktoken(UserTToken userTToken) {
        return null;
    }

    @Override
    public String getkey(UserT userT) {
        return null;
    }
}
