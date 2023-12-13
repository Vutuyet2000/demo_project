package com.rsupport.demo.service;

import com.rsupport.demo.dao.UserDao;
import com.rsupport.demo.dto.UserReq;
import com.rsupport.demo.dto.UserRes;
import com.rsupport.demo.entity.User;
import com.rsupport.demo.mapper.UserMapper;
import com.rsupport.demo.service.impl.UserServiceImpl;
import com.rsupport.demo.utils.FakeDataUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDao userDao;

    @Mock
    private UserMapper userMapper;

    private User user;

    private UserRes userRes;

    private UserReq userReq;

    @Before
    public void setUp() {
        user = FakeDataUtils.createUser();
        userRes = FakeDataUtils.createUserRes();
        userReq = FakeDataUtils.createUserReq();
    }

    @Test
    public void registerTest_Success() throws ParseException {
        Mockito.when(userDao.findByUsername(Mockito.any())).thenReturn(null);
        Mockito.when(userMapper.userReqToUser(Mockito.any())).thenReturn(user);
        Mockito.when(userMapper.userToUserRes(Mockito.any())).thenReturn(userRes);

        UserRes result = userService.register(userReq);

        Assertions.assertEquals(userRes.getId(), result.getId());
    }

    @Test
    public void registerTest_DuplicateUsername() throws ParseException {
        Mockito.when(userDao.findByUsername(Mockito.any())).thenReturn(user);

        UserRes result = userService.register(userReq);

        Assertions.assertEquals("Duplicate username", result.getMessage());
    }

    @Test
    public void loadUserTest_Success() throws ParseException {
        Mockito.when(userDao.findByUsernameAndActiveTrue(Mockito.any())).thenReturn(user);

        UserDetails result = userService.loadUserByUsername(FakeDataUtils.USERNAME);

        Assertions.assertEquals(userRes.getUsername(), result.getUsername());
    }

    @Test
    public void loadUserTest_NotFound() throws ParseException {
        Mockito.when(userDao.findByUsernameAndActiveTrue(Mockito.any())).thenReturn(null);

        UserDetails result = userService.loadUserByUsername(FakeDataUtils.USERNAME);

        Assertions.assertNull(result);
    }
}
