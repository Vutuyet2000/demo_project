package com.rsupport.demo.service.impl;

import com.rsupport.demo.dao.UserDao;
import com.rsupport.demo.dto.UserReq;
import com.rsupport.demo.dto.UserRes;
import com.rsupport.demo.entity.User;
import com.rsupport.demo.config.CustomUserDetails;
import com.rsupport.demo.mapper.UserMapper;
import com.rsupport.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUsernameAndActiveTrue(username);
    }

    @Override
    public UserRes register(UserReq userReq) {
        try {
            User existedUser = userDao.findByUsername(userReq.getUsername());
            if (existedUser != null) {
                UserRes userRes = new UserRes();
                userRes.setMessage("Duplicate username");
                return userRes;
            }

            User user = userMapper.userReqToUser(userReq);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(true);
            User savedUser = userDao.save(user);
            return userMapper.userToUserRes(savedUser);
        } catch (ParseException e) {
            log.error("REGISTER --> PARSE EXCEPTION {}", e);
            UserRes userRes = new UserRes();
            userRes.setMessage("Cannot Parse User!");
            return userRes;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsernameAndActiveTrue(username);
        if (user == null) {
            log.error("loadUserByUsername --> User Not Found with Username: {}", username);
            return null;
        }
        return new CustomUserDetails(user);
    }
}
