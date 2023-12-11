package com.rsupport.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "ATTACHMENT")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "URL")
    private String url;

//    @Column(name = "FILE_TYPE")
//    private String fileType;

//    @Column(name = "FILE_CONTENT")
//    private boolean fileContent;

    @ManyToOne
    @JoinColumn(name = "NOTICE_ID")
    private Notice notice;

}
