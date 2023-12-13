package com.rsupport.demo.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ViewReq {
    private UUID noticeId;
    private int view;
}
