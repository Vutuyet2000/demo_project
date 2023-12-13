package com.rsupport.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface AttachementService {
    public int save(MultipartFile file);
}
