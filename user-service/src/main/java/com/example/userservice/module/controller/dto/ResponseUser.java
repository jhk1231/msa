package com.example.userservice.module.controller.dto;

import lombok.Data;

@Data
public class ResponseUser {
    private String email;
    private String password;
    private String userId;
}
