package com.rsupport.demo.dao;

import com.rsupport.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDao extends JpaRepository<User, UUID> {
    public User findByUsername(String username);

    public User findByUsernameAndActiveTrue(String username);
}
