package com.rsupport.demo.dto;

import lombok.*;

import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String username;
}
