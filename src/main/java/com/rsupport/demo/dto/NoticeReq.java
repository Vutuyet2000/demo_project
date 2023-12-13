package com.rsupport.demo.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeReq {
    private UUID id;
    @NotNull(message = "Title cannot be null")
    private String title;
    @NotNull(message = "Content cannot be null")
    private String content;
    private String active;
    private String startDate;
    private String endDate;
    private String registrationDate;
    private String author;
}
