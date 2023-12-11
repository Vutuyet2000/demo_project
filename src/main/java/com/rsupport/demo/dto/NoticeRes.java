package com.rsupport.demo.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class NoticeRes {
    private UUID id;
    private String title;
    private String content;
    private String startDate;
    private String endDate;
    private String registrationDate;
    private String author;
    private String views;
}
