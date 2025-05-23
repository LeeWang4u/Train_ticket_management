package com.tauhoa.train.dtos.request;

import lombok.Data;

import java.util.List;

@Data
public class UserCreateRequestDto {
    private String userName;
//    private int userId;
    private String email;
    private String password;
}
