package com.tauhoa.train.dtos.request;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}

