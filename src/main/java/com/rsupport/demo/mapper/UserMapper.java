package com.rsupport.demo.mapper;

import com.rsupport.demo.dto.UserReq;
import com.rsupport.demo.dto.UserRes;
import com.rsupport.demo.entity.User;
import com.rsupport.demo.service.NoticeService;
import com.rsupport.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserMapper {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    private UserService userService;

    public User userReqToUser (UserReq userReq) throws ParseException {
        User user = new User();
        user.setUsername(userReq.getUsername());
        user.setPassword(userReq.getPassword());
        user.setActive(userReq.isActive());
        if(userReq.getCreatedDate() != null) {
            user.setCreatedDate(dateFormat.parse(userReq.getCreatedDate()));
        }

        return user;
    }
    public UserRes userToUserRes (User user){
        UserRes userRes = new UserRes();
        userRes.setId(user.getId());
        userRes.setUsername(user.getUsername());

        return userRes;
    }

    public User userDetailsToUser(UserDetails userDetails) {
        User user = getUserByUsername(userDetails.getUsername());
        return user;
    }

    private User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }
}
