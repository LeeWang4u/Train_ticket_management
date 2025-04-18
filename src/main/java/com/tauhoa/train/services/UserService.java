package com.tauhoa.train.services;

import com.tauhoa.train.dtos.request.LoginRequestDto;
import com.tauhoa.train.dtos.request.UserCreateRequestDto;
import com.tauhoa.train.models.User;
import com.tauhoa.train.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User create(UserCreateRequestDto userCreateRequestDto) {
        if (userRepository.findByEmail(userCreateRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("UserID is already in use.");
        }

        userCreateRequestDto.setPassword(passwordEncoder.encode(userCreateRequestDto.getPassword()));
        User user = User.toEntity(userCreateRequestDto);
        return userRepository.save(user);
    }

    public User login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Login Failed! There's no such user."));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password!");
        }

        return user;
    }
}
