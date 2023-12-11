package com.rsupport.demo.mapper;

import com.rsupport.demo.dto.NoticeReq;
import com.rsupport.demo.dto.NoticeRes;
import com.rsupport.demo.entity.Notice;
import com.rsupport.demo.entity.User;
import com.rsupport.demo.entity.View;
import com.rsupport.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class NoticeMapper {
    @Autowired
    private UserService userService;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public NoticeRes noticeToNoticeRes(Notice notice) {


        NoticeRes noticeRes = noticeToNoticeRes(notice);
        noticeRes.setId(notice.getId());
        noticeRes.setTitle(notice.getTitle());
        noticeRes.setContent(notice.getContent());
        noticeRes.setStartDate(dateFormat.format(notice.getStartDate()));
        noticeRes.setEndDate(dateFormat.format(notice.getEndDate()));
        noticeRes.setRegistrationDate(noticeRes.getRegistrationDate());
        noticeRes.setAuthor(notice.getAuthor().getUsername());
        noticeRes.setViews(totalViews(notice.getViews()).toString());

        return noticeRes;
    }

    private Integer totalViews (List<View> views) {
        Integer total = 0;

        for (View view : views) {
            total += view.getViews();
        }

        return total;
    }

    public Notice noticeReqToNotice(NoticeReq noticeReq) throws ParseException {
        Notice notice = new Notice();
        notice.setTitle(noticeReq.getTitle());
        notice.setContent(noticeReq.getContent());
        notice.setStartDate(dateFormat.parse(noticeReq.getStartDate()));
        notice.setEndDate(dateFormat.parse(noticeReq.getEndDate()));
        notice.setRegistrationDate(dateFormat.parse(noticeReq.getRegistrationDate()));
        notice.setAuthor(getUserByUsername(noticeReq.getAuthor()));

        return notice;
    }

    private User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }
}
