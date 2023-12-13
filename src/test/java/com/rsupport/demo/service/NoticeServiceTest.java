package com.rsupport.demo.service;

import com.rsupport.demo.dao.AttachmentDao;
import com.rsupport.demo.dao.NoticeDao;
import com.rsupport.demo.dto.NoticeReq;
import com.rsupport.demo.dto.NoticeRes;
import com.rsupport.demo.entity.Notice;
import com.rsupport.demo.mapper.NoticeMapper;
import com.rsupport.demo.service.impl.NoticeServiceImpl;
import com.rsupport.demo.utils.FakeDataUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)
public class NoticeServiceTest {
    @InjectMocks
    private NoticeServiceImpl noticeService;

    @Mock
    private NoticeDao noticeDao;

    @Mock
    private NoticeMapper noticeMapper;

    @Mock
    private AttachmentDao attachmentDao;

    private List<Notice> notices;

    private Notice notice;

    private NoticeReq noticeReq;

    private NoticeRes noticeRes;

    @Before
    public void setUp() {
        notices = FakeDataUtils.createNoticeList();
        notice = FakeDataUtils.createNotice();
        noticeReq = FakeDataUtils.createNoticeReq();
        noticeRes = FakeDataUtils.createNoticeRes();
    }

    @Test
    public void getNoticesTest_Success() {
        Mockito.when(noticeDao.findByStartDateBeforeAndEndDateAfter(Mockito.any())).thenReturn(notices);
        List<NoticeRes> results = noticeService.getActiveNotices();

        Assertions.assertEquals(notices.size(), results.size());
    }

    @Test
    public void getNoticeTest_Success() {
        Mockito.when(noticeDao.findById(Mockito.any())).thenReturn(Optional.of(notice));
        Notice result = noticeService.getNotice(notice.getId());

        Assertions.assertEquals(notice.getId(), result.getId());
    }

    @Test
    public void getNoticeTest_NotFound() {
        Mockito.when(noticeDao.findById(Mockito.any())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> noticeService.getNotice(notice.getId()));
    }

    @Test
    public void createNoticeTest_Success() throws ParseException {
        String name = "test.txt";
        String originalFileName = "test.txt";
        String contentType = "text/plain";
        MultipartFile[] attachments = new MockMultipartFile[]{new MockMultipartFile(name,
                originalFileName, contentType, "content".getBytes())};

        Mockito.when(noticeMapper.noticeReqToNotice(Mockito.any())).thenReturn(notice);
        Mockito.when(noticeDao.save(notice)).thenReturn(notice);

        int result = noticeService.createNotice(noticeReq, attachments);

        Assertions.assertEquals(1, result);
    }

    @Test
    public void createNoticeTest_Fail() throws ParseException {
        String name = "test.txt";
        String originalFileName = "test.txt";
        String contentType = "text/plain";
        MultipartFile[] attachments = new MockMultipartFile[]{new MockMultipartFile(name,
                originalFileName, contentType, "content".getBytes())};

        Mockito.when(noticeMapper.noticeReqToNotice(Mockito.any())).thenThrow(ParseException.class);

        int result = noticeService.createNotice(noticeReq, attachments);

        Assertions.assertEquals(0, result);
    }

    @Test
    public void updateNoticeTest_Success() throws ParseException {
        Notice existedNotice = FakeDataUtils.createNotice();
        existedNotice.setContent("existed content");
        Notice updatedNotice = existedNotice;
        updatedNotice.setContent(notice.getContent());

        String name = "test.txt";
        String originalFileName = "test.txt";
        String contentType = "text/plain";
        MultipartFile[] attachments = new MockMultipartFile[]{new MockMultipartFile(name,
                originalFileName, contentType, "content".getBytes())};

        Mockito.when(noticeMapper.noticeReqToNotice(Mockito.any())).thenReturn(notice);
        Mockito.when(noticeDao.findByIdAndActiveTrue(Mockito.any())).thenReturn(existedNotice);
        Mockito.when(noticeMapper.souNoticeToTarNoticeWithoutAttachments(notice,existedNotice)).thenReturn(updatedNotice);
        Mockito.when(noticeDao.save(notice)).thenReturn(updatedNotice);

        int result = noticeService.updateNotice(noticeReq, "tester", attachments);

        Assertions.assertEquals(1, result);
    }

    @Test
    public void updateNoticeTest_Fail() throws ParseException {
        Notice existedNotice = FakeDataUtils.createNotice();
        existedNotice.setContent("existed content");
        Notice updatedNotice = existedNotice;
        updatedNotice.setContent(notice.getContent());

        String name = "test.txt";
        String originalFileName = "test.txt";
        String contentType = "text/plain";
        MultipartFile[] attachments = new MockMultipartFile[]{new MockMultipartFile(name,
                originalFileName, contentType, "content".getBytes())};

        Mockito.when(noticeMapper.noticeReqToNotice(Mockito.any())).thenThrow(ParseException.class);

        int result = noticeService.updateNotice(noticeReq, "tester", attachments);

        Assertions.assertEquals(0, result);
    }

    @Test
    public void deleteNoticeTest_Success() {
        noticeRes.setActive(false);

        Mockito.when(noticeDao.findById(Mockito.any())).thenReturn(Optional.ofNullable(notice));
        Mockito.when(noticeMapper.noticeToNoticeRes(Mockito.any())).thenReturn(noticeRes);

        NoticeRes result = noticeService.deleteNotice(notice.getId());

        Assertions.assertEquals(false, result.isActive());
    }

    @Test
    public void deleteNoticeTest_Fail() {
        Mockito.when(noticeDao.findById(Mockito.any())).thenThrow(NoSuchElementException.class);

        NoticeRes result = noticeService.deleteNotice(notice.getId());

        Assertions.assertEquals("Not Found Notice!", result.getMessage());
    }
}
