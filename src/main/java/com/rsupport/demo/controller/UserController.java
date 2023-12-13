package com.rsupport.demo.controller;

import com.rsupport.demo.config.CustomUserDetails;
import com.rsupport.demo.config.JwtTokenProvider;
import com.rsupport.demo.dto.LoginReq;
import com.rsupport.demo.dto.LoginRes;
import com.rsupport.demo.dto.UserReq;
import com.rsupport.demo.dto.UserRes;
import com.rsupport.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginReq loginReq) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginReq.getUsername(),
                        loginReq.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        LoginRes result = new LoginRes(jwt);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserReq registrationReq) {
        UserRes result = userService.register(registrationReq);
        return result.getMessage() != null ? ResponseEntity.ok().body(result)
                : ResponseEntity.badRequest().body(result);
    }
}
