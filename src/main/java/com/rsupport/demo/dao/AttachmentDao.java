package com.rsupport.demo.dao;

import com.rsupport.demo.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AttachmentDao extends JpaRepository<Attachment, UUID> {
}
