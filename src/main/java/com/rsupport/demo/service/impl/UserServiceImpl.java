package com.rsupport.demo.service.impl;

import com.rsupport.demo.dao.UserDao;
import com.rsupport.demo.entity.User;
import com.rsupport.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
