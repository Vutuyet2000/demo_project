package com.rsupport.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
public class UserReq {
    private UUID id;
    @NotBlank(message = "username cannot be null or blank")
    private String username;
    @NotNull(message = "password cannot be null")
    private String password;
    private boolean active;
    private String createdDate;
}
