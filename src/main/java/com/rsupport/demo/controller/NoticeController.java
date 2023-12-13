package com.rsupport.demo.controller;

import com.rsupport.demo.dto.NoticeReq;
import com.rsupport.demo.dto.NoticeRes;
import com.rsupport.demo.dto.ViewReq;
import com.rsupport.demo.entity.View;
import com.rsupport.demo.mapper.UserMapper;
import com.rsupport.demo.service.AttachementService;
import com.rsupport.demo.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private AttachementService attachementService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/get-notices")
    public ResponseEntity<Object> getNotices() {
        List<NoticeRes> results = noticeService.getActiveNotices();
        return ResponseEntity.ok().body(results);
    }

    @PostMapping(name = "/create-notice", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createNotice(@Valid @RequestPart("noticeReq") NoticeReq noticeReq,
                                               @RequestPart("attachments") MultipartFile[] attachmentsReq) {
        int result = noticeService.createNotice(noticeReq, attachmentsReq);
        return result == 1 ? ResponseEntity.ok().body("Created Notice Successfully!")
                : ResponseEntity.badRequest().body("Failed to Create Notice!");

    }

    @PutMapping(name = "/update-notices", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateNotice(@Valid @RequestPart("noticeReq") NoticeReq noticeReq,
                                               @RequestPart("attachments") MultipartFile[] attachmentsReq,
                                               Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int result = noticeService.updateNotice(noticeReq,userDetails.getUsername(), attachmentsReq);
        return result == 1 ? ResponseEntity.ok().body("Updated Notice Successfully!")
                : ResponseEntity.badRequest().body("Failed to Update Notice!");
    }

    @RequestMapping("/delete-notice/{id}")
    public ResponseEntity<Object> deleteNotice(@PathVariable UUID id) {
        NoticeRes result = noticeService.deleteNotice(id);
        return result.getMessage() != null ? ResponseEntity.ok().body(result)
                : ResponseEntity.badRequest().body(result);
    }

    @RequestMapping("/add-view")
    public ResponseEntity<Object> addView(@RequestBody ViewReq viewReq, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        View result = noticeService.addView(viewReq, userMapper.userDetailsToUser(userDetails));
        return result != null ? ResponseEntity.ok().body(result)
                : ResponseEntity.badRequest().body("Failed to Add View!");
    }
}
