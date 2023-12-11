package com.rsupport.demo.dto;

import com.rsupport.demo.entity.Attachment;
import lombok.Data;

import java.util.List;

@Data
public class NoticeAndFileReq {
    private NoticeReq noticeReq;
    private List<Attachment> attachments;
}
