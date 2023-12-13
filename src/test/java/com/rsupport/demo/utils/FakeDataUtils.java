package com.rsupport.demo.utils;

import com.rsupport.demo.dto.NoticeReq;
import com.rsupport.demo.dto.NoticeRes;
import com.rsupport.demo.dto.UserReq;
import com.rsupport.demo.dto.UserRes;
import com.rsupport.demo.entity.Attachment;
import com.rsupport.demo.entity.Notice;
import com.rsupport.demo.entity.User;
import com.rsupport.demo.enums.RoleEnum;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class FakeDataUtils {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private static Date dt = new Date();

    public static final String USERNAME = "tester";

    private static final String PASSWORD = "tester";

    private static final String FILE_NAME = "test.txt";

    private static final String URL = "./attachments/test.txt";

    public static User createUser() {
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setActive(true);
        user.setRole(RoleEnum.ROLE_USER.name());
        user.setId(UUID.randomUUID());

        return user;
    }

    public static UserRes createUserRes() {
        UserRes userRes = new UserRes();
        userRes.setUsername(USERNAME);
        userRes.setId(UUID.randomUUID());

        return userRes;
    }

    public static UserReq createUserReq() {
        UserReq userReq = new UserReq();
        userReq.setUsername(USERNAME);
        userReq.setActive(true);
        userReq.setId(UUID.randomUUID());

        return userReq;
    }

    public static Notice createNotice() {
        User author = createUser();
        Notice notice = new Notice(UUID.randomUUID(), "title 1", "content 1", true, dt,
                DateUtils.addDays(dt,3), dt, null,null, author, null, null);
        return notice;
    }

    public static List<Notice> createNoticeList() {
        List<Notice> notices = new ArrayList<>();
        User author = createUser();
        notices.add(new Notice(UUID.randomUUID(), "title 1", "content 1", true, dt,
                DateUtils.addDays(dt,3), dt, null,null, author, null, null));
        notices.add(new Notice(UUID.randomUUID(), "title 2", "content 2", true, dt,
                DateUtils.addDays(dt,2), dt, null,null, author, null, null));
        notices.add(new Notice(UUID.randomUUID(), "title 3", "content 3", true, dt,
                DateUtils.addDays(dt,4), dt, null,null, author, null, null));

        return notices;
    }

    public static Attachment createAttachment() {
        Attachment attachment = new Attachment();
        attachment.setId(UUID.randomUUID());
        attachment.setFileName(FILE_NAME);
        attachment.setUrl(URL);
        return attachment;
    }

    public static NoticeReq createNoticeReq() {
        NoticeReq noticeReq = new NoticeReq(UUID.randomUUID(), "title 1", "content 1", "true"
                , simpleDateFormat.format(dt), simpleDateFormat.format(DateUtils.addDays(dt,3))
                , simpleDateFormat.format(dt),USERNAME);
        return noticeReq;
    }

    public static NoticeRes createNoticeRes() {
        NoticeRes noticeRes = new NoticeRes(UUID.randomUUID(), "title 1", "content 1", true
                , simpleDateFormat.format(dt), simpleDateFormat.format(DateUtils.addDays(dt,3))
                , simpleDateFormat.format(dt),USERNAME,"0",null);

        return noticeRes;
    }
}
