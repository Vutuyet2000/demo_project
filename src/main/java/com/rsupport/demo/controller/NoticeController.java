package com.rsupport.demo.controller;

import com.rsupport.demo.dto.NoticeReq;
import com.rsupport.demo.dto.NoticeRes;
import com.rsupport.demo.service.AttachementService;
import com.rsupport.demo.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private AttachementService attachementService;

    @GetMapping("/get-notices")
    public ResponseEntity<Object> getNotices() {
        List<NoticeRes> results = noticeService.getActiveNotices();
        return ResponseEntity.ok().body(results);
    }

    @PostMapping(name = "/create-notice", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createNotice(@RequestPart("noticeReq") NoticeReq noticeReq,
                                               @RequestPart("attachments") MultipartFile[] attachmentsReq) {
        int result = noticeService.createNotice(noticeReq, attachmentsReq);
        return result == 1 ? ResponseEntity.ok().body("Created Notice Successfully!")
                : ResponseEntity.badRequest().body("Failed to Create Notice!");

    }

    @PutMapping(name = "/update-notices", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateNotice(@RequestPart("noticeReq") NoticeReq noticeReq,
                                               @RequestPart("attachments") MultipartFile[] attachmentsReq) {
        int result = noticeService.updateNotice(noticeReq,"", attachmentsReq);
        return result == 1 ? ResponseEntity.ok().body("Updated Notice Successfully!")
                : ResponseEntity.badRequest().body("Failed to Update Notice!");
    }

    @RequestMapping("/delete-notice/{id}")
    public ResponseEntity<Object> deleteNotice(@PathVariable UUID id) {
        int result = noticeService.deleteNotice(id);
        return result == 1 ? ResponseEntity.ok().body("Deleted Notice Successfully!")
                : ResponseEntity.badRequest().body("Failed to Delete Notice!");
    }
}
