package com.vito.jnotsj.vo.SignIn;

import com.vito.jnotsj.entity.RoleName;
import com.vito.jnotsj.entity.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private User user;
}