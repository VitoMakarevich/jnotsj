package com.vito.jnotsj.auth.vo.SignIn;

import com.vito.jnotsj.auth.entity.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private final String tokenType = "Bearer";
    private String refreshToken;
    private User user;
}