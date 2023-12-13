package com.rsupport.demo.service.impl;

import com.rsupport.demo.dao.AttachmentDao;
import com.rsupport.demo.dao.NoticeDao;
import com.rsupport.demo.dao.ViewDao;
import com.rsupport.demo.dto.NoticeReq;
import com.rsupport.demo.dto.NoticeRes;
import com.rsupport.demo.dto.ViewReq;
import com.rsupport.demo.entity.Attachment;
import com.rsupport.demo.entity.Notice;
import com.rsupport.demo.entity.User;
import com.rsupport.demo.entity.View;
import com.rsupport.demo.mapper.NoticeMapper;
import com.rsupport.demo.service.NoticeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private ViewDao viewDao;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private final Path root = Paths.get("./src/main/resources/attachments/");

    @Override
    public int createNotice(NoticeReq noticeReq, MultipartFile[] attachments) {
        try {
            Notice notice = noticeMapper.noticeReqToNotice(noticeReq);
            notice.setRegistrationDate(new Date());
            notice.setActive(true);
            Notice result = noticeDao.save(notice);

            List<Attachment> attachmentList = new ArrayList<>();
            Arrays.asList(attachments).stream().forEach(file -> {
                Attachment savedAttachement = saveAttachements(file);
                if (savedAttachement != null) {
                    savedAttachement.setNotice(result);
                    attachmentList.add(savedAttachement);
                }
            });
            attachmentDao.saveAll(attachmentList);
            return 1;
        } catch (IllegalArgumentException e) {
            return 0;
        } catch (ParseException e) {
            return 0;
        }
    }

    private Attachment saveAttachements(MultipartFile attachmentReq) {
        try {
            String storagePath = String.valueOf(this.root.resolve(attachmentReq.getOriginalFilename()));
            if(!attachmentReq.getOriginalFilename().isEmpty()) {
                File newFile = new File(storagePath);
                if (!newFile.exists()) {
                    newFile.createNewFile();
                    attachmentReq.transferTo(newFile);
                }
                else {
                    Files.copy(attachmentReq.getInputStream(), this.root.resolve(attachmentReq.getOriginalFilename())
                            , StandardCopyOption.REPLACE_EXISTING);
                }

                Attachment attachment = new Attachment();
                attachment.setFileName(attachmentReq.getOriginalFilename());
                attachment.setUrl(String.valueOf(this.root.resolve(attachmentReq.getOriginalFilename())));
                return attachment;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public int updateNotice(NoticeReq noticeReq, String username, MultipartFile[] attachments) {
        try {
            Notice updateNotice = noticeMapper.noticeReqToNotice(noticeReq);
            updateNotice.setUpdatedDate(new Date());
            updateNotice.setUpdateUser(username);

            Notice notice = checkExistedAndUpdateNotice(updateNotice);
            if(notice == null) {
                return -1;
            }
            Notice result = noticeDao.save(notice);

            List<Attachment> attachmentList = new ArrayList<>();
            Arrays.asList(attachments).stream().forEach(file -> {
                Attachment savedAttachement = saveAttachements(file);
                if (savedAttachement != null) {
                    savedAttachement.setNotice(result);
                    attachmentList.add(savedAttachement);
                }
            });
            attachmentDao.saveAll(attachmentList);

            return 1;
        } catch (IllegalArgumentException e) {
            return 0;
        } catch (ParseException e) {
            return 0;
        }
    }

    private Notice checkExistedAndUpdateNotice(Notice updateNotice) {
        Notice existedNotice = noticeDao.findByIdAndActiveTrue(updateNotice.getId());
        if(existedNotice != null) {
            return noticeMapper.souNoticeToTarNoticeWithoutAttachments(updateNotice, existedNotice);
        }
        return null;
    }

    @Override
    public NoticeRes deleteNotice(UUID id) {
        try {
            Notice notice = noticeDao.findById(id).get();
            notice.setActive(false);
            Notice result = noticeDao.save(notice);

            return noticeMapper.noticeToNoticeRes(result);
        } catch (NoSuchElementException e) {
            NoticeRes noticeRes = new NoticeRes();
            noticeRes.setMessage("Not Found Notice!");
            return noticeRes;
        }
    }

    @Override
    public List<NoticeRes> getActiveNotices() {
        List<NoticeRes> results = new ArrayList<>();

        List<Notice> notices = noticeDao.findByStartDateBeforeAndEndDateAfter(new Date());
        if(!notices.isEmpty()) {
            for (Notice notice : notices) {
                NoticeRes result = noticeMapper.noticeToNoticeRes(notice);
                results.add(result);
            }
        }

        return results;
    }

    @Override
    public Notice getNotice(UUID id) {
        return noticeDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Notice Not Found."));
    }

    @Override
    public View addView(ViewReq vieẉ̣̣̣̣̣Req̣, User user) {
        View view = noticeMapper.viewReqToView(vieẉ̣̣̣̣̣Req̣);
        view.setUser(user);
        return viewDao.save(view);
    }
}
