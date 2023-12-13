package com.rsupport.demo.mapper;

import com.rsupport.demo.dto.NoticeReq;
import com.rsupport.demo.dto.NoticeRes;
import com.rsupport.demo.dto.ViewReq;
import com.rsupport.demo.entity.Notice;
import com.rsupport.demo.entity.User;
import com.rsupport.demo.entity.View;
import com.rsupport.demo.service.NoticeService;
import com.rsupport.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Component
public class NoticeMapper {
    @Autowired
    private UserService userService;

    @Autowired
    private NoticeService noticeService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public NoticeRes noticeToNoticeRes(Notice notice) {
        NoticeRes noticeRes = new NoticeRes();
        noticeRes.setId(notice.getId());
        noticeRes.setTitle(notice.getTitle());
        noticeRes.setContent(notice.getContent());
        noticeRes.setActive(notice.isActive());
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
        notice.setId(noticeReq.getId());
        notice.setTitle(noticeReq.getTitle());
        notice.setContent(noticeReq.getContent());
        notice.setStartDate(dateFormat.parse(noticeReq.getStartDate()));
        notice.setEndDate(dateFormat.parse(noticeReq.getEndDate()));
        notice.setRegistrationDate(dateFormat.parse(noticeReq.getRegistrationDate()));
        notice.setAuthor(getUserByUsername(noticeReq.getAuthor()));
        if(noticeReq.getActive() != null) {
            notice.setActive(Boolean.getBoolean(noticeReq.getActive()));
        }

        return notice;
    }

    private User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    public Notice souNoticeToTarNoticeWithoutAttachments(Notice source, Notice target) {
        if (source.getTitle() != null) {
            target.setTitle(source.getTitle());
        }
        if (source.getContent() != null) {
            target.setContent(source.getContent());
        }
        if (source.getStartDate() != null) {
            target.setStartDate(source.getStartDate());
        }
        if (source.getEndDate() != null) {
            target.setEndDate(source.getEndDate());
        }
        if (source.getRegistrationDate() != null) {
            target.setRegistrationDate(source.getRegistrationDate());
        }
        target.setActive(source.isActive());
        if (source.getAuthor() != null) {
            target.setAuthor(source.getAuthor());
        }
        return target;
    }

    public View viewReqToView(ViewReq viewReq) {
        View view = new View();
        view.setViews(viewReq.getView());
        view.setNotice(getNotice(viewReq.getNoticeId()));
        return view;
    }

    private Notice getNotice(UUID id) {
        return noticeService.getNotice(id);
    }

}
