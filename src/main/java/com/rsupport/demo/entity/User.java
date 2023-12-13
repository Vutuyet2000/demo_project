package com.rsupport.demo.entity;

import com.rsupport.demo.enums.RoleEnum;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "USERS")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ACTIVE")
    private boolean active = true;

    @Column(name = "ROLE")
    private String role = RoleEnum.ROLE_USER.toString();

    @Column(name = "CREATED_DATE")
    private Date createdDate = new Date();

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Notice> notices;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<View> views;
}
