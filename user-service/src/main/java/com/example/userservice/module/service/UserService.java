package com.example.userservice.module.service;

import com.example.userservice.module.dto.UserDto;
import org.springframework.stereotype.Service;


public interface UserService {

    UserDto createUser(UserDto userDto);
}
