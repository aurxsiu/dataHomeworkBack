package com.aurxsiu.datahomework.controller;

import com.aurxsiu.datahomework.request.LoginRequest;
import com.aurxsiu.datahomework.request.RegisterRequest;
import com.aurxsiu.datahomework.response.LoginResponse;
import com.aurxsiu.datahomework.response.RegisterResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return new LoginResponse(1);
    }
    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request){
        return new RegisterResponse(2);
    }
}

