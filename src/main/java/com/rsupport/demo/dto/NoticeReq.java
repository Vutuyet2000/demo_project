package com.rsupport.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
public class NoticeReq {
    private UUID id;
    private String title;
    private String content;
    private String startDate;
    private String endDate;
    private String registrationDate;
    private String author;
}
