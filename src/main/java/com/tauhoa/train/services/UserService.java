package com.tauhoa.train.services;

import com.tauhoa.train.dtos.request.LoginRequestDto;
import com.tauhoa.train.dtos.request.UserCreateRequestDto;
import com.tauhoa.train.dtos.request.ChangePasswordRequestDto;
import com.tauhoa.train.models.User;
import com.tauhoa.train.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public User create(UserCreateRequestDto userCreateRequestDto) {
        if (userRepository.findByEmail(userCreateRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("This mail is already in use.");
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

    public User changePassword(ChangePasswordRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(("Change Password Failed! There's no such user")));

        String key = "OTP::" + dto.getEmail();
        String storedOtp = (String) redisTemplate.opsForValue().get(key);

        if (storedOtp == null || !storedOtp.equals(dto.getOtp())) {
            throw new IllegalArgumentException("Invalid or expired OTP.");
        }

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password!");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        redisTemplate.delete(key);

        return userRepository.save(user);
    }
}