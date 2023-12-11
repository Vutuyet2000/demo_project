package com.rsupport.demo.service.impl;

import com.rsupport.demo.dao.AttachmentDao;
import com.rsupport.demo.dao.NoticeDao;
import com.rsupport.demo.dto.NoticeReq;
import com.rsupport.demo.dto.NoticeRes;
import com.rsupport.demo.entity.Attachment;
import com.rsupport.demo.entity.Notice;
import com.rsupport.demo.mapper.NoticeMapper;
import com.rsupport.demo.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private final Path root = Paths.get("./src/main/resources/attachments/");

    @Override
    public int createNotice(NoticeReq noticeReq, MultipartFile[] attachments) {
        try {
            Notice notice = noticeMapper.noticeReqToNotice(noticeReq);
            notice.setRegistrationDate(new Date());
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
//            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int updateNotice(NoticeReq noticeReq, String username, MultipartFile[] attachments) {
        try {
            Notice notice = noticeMapper.noticeReqToNotice(noticeReq);
            notice.setUpdatedDate(new Date());
            notice.setUpdateUser(username);
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

    @Override
    public int deleteNotice(UUID id) {
        try {
            Notice notice = noticeDao.findById(id).get();
            notice.setActive(false);
            noticeDao.save(notice);
            return 1;
        } catch (EmptyResultDataAccessException e) {
            return 0;
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
}
