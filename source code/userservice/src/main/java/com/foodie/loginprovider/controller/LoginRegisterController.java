package com.foodie.loginprovider.controller;
import com.foodie.dto.ServerResponse;
import com.foodie.dto.UserD;
import com.foodie.pojo.User;
import com.foodie.service.LoginService;
import com.foodie.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginRegisterController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RegisterService registerService;

    @PostMapping("/login")
    public UserD login(@RequestBody User user) {
        return loginService.findUser(user);
    }


    @PostMapping("/register/user")
    public ServerResponse registerUser(@RequestBody UserD user) {
        return registerService.registerUser(user);
    }



}
