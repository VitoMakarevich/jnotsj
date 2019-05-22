package com.vito.jnotsj.vo;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType ="Bearer";
}