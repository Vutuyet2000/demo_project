package com.rsupport.demo.service;

import com.rsupport.demo.config.CustomUserDetails;
import com.rsupport.demo.dto.NoticeReq;
import com.rsupport.demo.dto.NoticeRes;
import com.rsupport.demo.dto.ViewReq;
import com.rsupport.demo.entity.Notice;
import com.rsupport.demo.entity.User;
import com.rsupport.demo.entity.View;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface NoticeService {
    public int createNotice(NoticeReq notice, MultipartFile[] attachments);

    public int updateNotice(NoticeReq notice, String username, MultipartFile[] attachments);

    public NoticeRes deleteNotice(UUID id);

    public List<NoticeRes> getActiveNotices();

    public Notice getNotice(UUID id);

    public View addView(ViewReq vieẉ̣̣̣̣̣̣, User user);
}
