package com.vito.jnotsj.controller;


import com.vito.jnotsj.service.AuthService;
import com.vito.jnotsj.vo.SignIn.LoginRequest;
import com.vito.jnotsj.vo.SignIn.LoginResponse;
import com.vito.jnotsj.vo.signUp.SignUpRequest;
import com.vito.jnotsj.vo.signUp.SignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public SignUpResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return this.authService.signUp(signUpRequest);
    }

    @PostMapping("/signin")
    public LoginResponse signIn(@RequestBody @Valid LoginRequest loginRequest) {
        return this.authService.signIn(loginRequest);
    }
}