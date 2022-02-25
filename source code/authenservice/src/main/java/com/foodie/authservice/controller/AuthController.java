package com.foodie.authservice.controller;
import com.foodie.authservice.Authservice;
import com.foodie.dto.UserD;
import com.foodie.dto.UserDToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
public class AuthController {
    @Autowired
    Authservice authservice;
    @PostMapping("/auth/createtoken")
    public String createtoken(@NotNull @RequestBody UserD userD){ return authservice.createtoken(userD); }
    @GetMapping("/auth/refreshtoken")
    public Boolean refreshtoken(@NotNull  @RequestParam("key") String key){ return authservice.refreshtoken(key); }
    @PostMapping("/auth/refreshtokenByUserD")
    public boolean refreshtoken(@NotNull UserD userD){ return authservice.refreshtoken(userD); }
    @GetMapping("/auth/gettokenobject")
    public UserD gettokenobject(@NotNull @RequestParam("token") String token){return  authservice.getTokenObject(token);}
    @GetMapping("/auth/checktokenbykey")
    public Boolean checktokenbykey(@NotNull @RequestParam("key") String key,@NotNull @RequestParam("token")String token){return authservice.checkTokenByKey(key,token);}
    @PostMapping("/auth/checktokenbyuserd")
    public Boolean checktoken(@NotNull @RequestBody UserDToken userDToken){
        return authservice.checkToken(userDToken.getUserD(),userDToken.getToken());}
    @PostMapping("/auth/getkey")
    public String getkey(@NotNull @RequestBody UserD userD){ return authservice.getkey(userD);}



}
