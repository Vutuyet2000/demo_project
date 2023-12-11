package com.rsupport.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "VIEW")
//@IdClass(ViewIds.class)
public class View implements Serializable {
    @Id
    @Column(name = "ID")
    private UUID Id;
//
//    @Column(name = "USER_ID")
//    private UUID userId;
//
//    @Column(name = "NOTICE_ID")
//    private UUID noticeId;

    @Column(name = "VIEWS")
    private Integer views;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "NOTICE_ID")
    private Notice notice;
}
