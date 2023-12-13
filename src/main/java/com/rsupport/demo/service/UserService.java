package com.rsupport.demo.service;

import com.rsupport.demo.dto.UserReq;
import com.rsupport.demo.dto.UserRes;
import com.rsupport.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
//    public UserDetails loadUserByUsername(String username);

    public User getUserByUsername(String username);

    public UserRes register(UserReq userReq);
}
