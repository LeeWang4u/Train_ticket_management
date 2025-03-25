package com.tauhoa.train.services;

import com.tauhoa.train.dtos.UserDTO;
import com.tauhoa.train.models.User;
import com.tauhoa.train.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    @Override
    public User save(UserDTO userDTO){
        User user = new User(userDTO.getEmail(),userDTO.getPhone(),userDTO.getCccd(),userDTO.getFullName());
        return userRepository.save(user);
    }
}
