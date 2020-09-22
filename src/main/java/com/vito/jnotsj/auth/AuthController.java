package com.vito.jnotsj.auth;


import com.vito.jnotsj.auth.service.AuthService;
import com.vito.jnotsj.auth.vo.SignIn.LoginRequest;
import com.vito.jnotsj.auth.vo.SignIn.LoginResponse;
import com.vito.jnotsj.auth.vo.signUp.SignUpRequest;
import com.vito.jnotsj.auth.vo.signUp.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public SignUpResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return this.authService.signUp(signUpRequest);
    }

    @PostMapping("/signin")
    public LoginResponse signIn(@RequestBody @Valid LoginRequest loginRequest) {
        return this.authService.signIn(loginRequest);
    }
}