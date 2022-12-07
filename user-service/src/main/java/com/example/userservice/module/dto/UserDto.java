package com.example.userservice.module.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private String email;
    private String password;
    private String name;
    private String userId;
    private Date createDate;
    private String encryptedPassword;
}
