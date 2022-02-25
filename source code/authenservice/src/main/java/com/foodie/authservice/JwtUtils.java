package com.foodie.authservice;

import com.foodie.dto.UserD;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * jwt工具类
 */
public class JwtUtils {

    public static final String SUBJECT = "foodiesquad";

    public static final long EXPIRE = 1000*60*60*24*7;  //过期时间，毫秒，一周
    //秘钥
    public static final  String APPSECRET = "fkjansdieno!";

    /**
     * 生成jwt
     * @param
     * @return
     */
    public static String geneJsonWebToken(UserD userd){

        if(userd == null ){
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("user",userd)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
                .signWith(SignatureAlgorithm.HS256,APPSECRET).compact();
        return token;
    }


    /**
     * 校验token
     * @param token
     * @return
     */
    public static Claims checkJWT(String token ){

        try{
            final Claims claims =  Jwts.parser().setSigningKey(APPSECRET).
                    parseClaimsJws(token).getBody();
            return  claims;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }



}