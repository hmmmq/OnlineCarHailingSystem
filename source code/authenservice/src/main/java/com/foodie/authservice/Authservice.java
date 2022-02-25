package com.foodie.authservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.foodie.dto.UserD;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class Authservice {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private String key="";
    private  Map<String,Object> claims=new DefaultClaims();

//    前提：没有找到token,但是检查后数据库有该usert，新建一个token返回
    public String createtoken( UserD userD){
        getkey(userD);
        String  tokenredis = JwtUtils.geneJsonWebToken(userD);
        stringRedisTemplate.opsForValue().set(key, tokenredis, Duration.ofDays(7));
        return tokenredis;
    }

    public Boolean refreshtoken(String key){
     return stringRedisTemplate.expire(key, 7, TimeUnit.DAYS);
    }

    /* 前提：已知token是有的,也已经延期，要检查是否是该用户本人
    * */
    public Boolean checkToken(UserD userD,String token){
        getkey(userD);
        String tokenredis = stringRedisTemplate.opsForValue().get(key);
        if (token==tokenredis)
            return true;
        return false;
    }

    public Boolean checkTokenByKey(String key,String token){
         String tokenredis = stringRedisTemplate.opsForValue().get(key);
         if (null==tokenredis)
             return false;
         if (token.equals(tokenredis))
         return true;

        return false;
    }

    /*
     * 1.获取token,
     * 2.通过token获得对象。
     * */
    public UserD getTokenObject(String token) {
        Claims claims=JwtUtils.checkJWT(token);
        return getUserD(claims);
    }



    public String getkey(UserD userD) {
        //1.奇数乘客
        //2.偶数司机
        key=""+userD.getType()+userD.getId();
        return key;
    }


    private UserD getUserD(Map<String, Object> claims) {
        Object  user= claims.get("user");
        UserD userD=JSON.parseObject(JSON.toJSONString(user), new TypeReference<UserD>() { });
        return userD;
    }


    public Boolean refreshtoken(UserD userD) {
        getkey(userD);
        refreshtoken(key);
        return true;
    }
}
