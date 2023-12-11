package com.rsupport.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import java.util.UUID;

@Data
public class ViewIds {
    private UUID userId;
    private UUID noticeId;

    public ViewIds(UUID userId, UUID noticeId) {
        this.userId = userId;
        this.noticeId = noticeId;
    }
}
