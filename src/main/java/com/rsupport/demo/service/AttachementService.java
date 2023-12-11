package com.rsupport.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface AttachementService {
    public void init();
    public int save(MultipartFile file);

//    public Resource load(String filename);
//
//    public Stream<Path> loadAll();
}
