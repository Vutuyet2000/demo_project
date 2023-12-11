package com.rsupport.demo.service;

import com.rsupport.demo.dto.NoticeReq;
import com.rsupport.demo.dto.NoticeRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface NoticeService {
    public int createNotice(NoticeReq notice, MultipartFile[] attachments);

    public int updateNotice(NoticeReq notice, String username, MultipartFile[] attachments);

    public int deleteNotice(UUID id);

    public List<NoticeRes> getActiveNotices();
}
