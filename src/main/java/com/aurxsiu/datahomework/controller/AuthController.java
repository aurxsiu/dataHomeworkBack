package com.aurxsiu.datahomework.controller;

import com.aurxsiu.datahomework.entity.User;
import com.aurxsiu.datahomework.request.LoginRequest;
import com.aurxsiu.datahomework.request.RegisterRequest;
import com.aurxsiu.datahomework.response.LoginResponse;
import com.aurxsiu.datahomework.response.RegisterResponse;
import com.aurxsiu.datahomework.util.FileHelper;
import com.aurxsiu.datahomework.util.JsonHelper;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        HashSet<User> users = FileHelper.getUser();
        for (User user : users) {
            if(user.getUsername().equals(request.getUsername())&&user.getPassword().equals(request.getPassword())){
                return new LoginResponse(user.getId());
            }
        }
        throw new RuntimeException("账号密码错误");
    }
    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request){
        try {
            HashSet<User> users = FileHelper.getUser();
            int maxId = 1;
            for (User user : users) {
                maxId = Math.max(maxId, user.getId() + 1);
            }
            users.add(new User(request.getUsername(), request.getPassword(), new HashMap<>(), maxId));
            FileHelper.setUserFile(JsonHelper.decode(users));
            return new RegisterResponse(maxId);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

