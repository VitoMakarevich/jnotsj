package com.vito.jnotsj.vo.SignIn;

import com.vito.jnotsj.entity.RoleName;
import com.vito.jnotsj.entity.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private final String tokenType = "Bearer";
    private String refreshToken;
    private User user;
}