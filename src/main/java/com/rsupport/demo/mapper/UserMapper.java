package com.rsupport.demo.mapper;

import com.rsupport.demo.dto.UserDTO;
import com.rsupport.demo.entity.User;
public interface UserMapper {
    public User userDtoToUser (UserDTO userDTO);
    public UserDTO userToUserDto (User user);
}
