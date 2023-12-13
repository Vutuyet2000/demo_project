package com.rsupport.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRes {
    private UUID id;
    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
}
