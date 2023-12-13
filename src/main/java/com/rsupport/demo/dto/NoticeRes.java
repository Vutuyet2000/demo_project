package com.rsupport.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeRes {
    private UUID id;
    private String title;
    private String content;
    private boolean active;
    private String startDate;
    private String endDate;
    private String registrationDate;
    private String author;
    private String views;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
}
