package com.rsupport.demo.service.impl;

import com.rsupport.demo.dao.AttachmentDao;
import com.rsupport.demo.entity.Attachment;
import com.rsupport.demo.service.AttachementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AttachementServiceImpl implements AttachementService {
    @Autowired
    private AttachmentDao attachmentDao;

    private final Path root = Paths.get("resources/attachments");

    @Override
    public int save(MultipartFile file) {
        try {
            if(!file.getOriginalFilename().isEmpty()) {
                Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
                Attachment attachment = new Attachment();
                attachment.setFileName(file.getOriginalFilename());
                attachment.setUrl(String.valueOf(this.root.resolve(file.getOriginalFilename())));
            }
        } catch (Exception e) {
            return 0;
//            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        return 1;
    }
}
