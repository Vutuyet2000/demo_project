package com.rsupport.demo.dto;

import lombok.Data;

@Data
public class LoginRes {
    private String accessToken;
    private String tokenType = "Bearer";

    public LoginRes(String accessToken) {
        this.accessToken = accessToken;
    }
}
