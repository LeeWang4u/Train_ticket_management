package com.tauhoa.train.services.impl;

import com.tauhoa.train.dtos.UserDTO;
import com.tauhoa.train.models.User;

public interface IUserService {
    User save(UserDTO userDTO);
//    void findByEmail(String email);
}
