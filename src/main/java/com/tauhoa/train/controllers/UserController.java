package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.request.ChangePasswordRequestDto;
import com.tauhoa.train.models.User;
import com.tauhoa.train.securities.JwtTokenProvider;
import com.tauhoa.train.securities.RefreshTokenService;
import com.tauhoa.train.services.EmailService;
import com.tauhoa.train.services.UserService;
import com.tauhoa.train.dtos.request.UserCreateRequestDto;
import com.tauhoa.train.dtos.request.LoginRequestDto;
import com.tauhoa.train.common.CommonResponse;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/currentUser")
    public ResponseEntity<CommonResponse> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "").trim();

        Claims claims = jwtTokenProvider.getClaims(token);
        String userId = claims.getSubject();

        User user = userService.findById(Long.parseLong(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user == null) {
            return new ResponseEntity<>(new CommonResponse(HttpStatus.UNAUTHORIZED, "User not found", null), HttpStatus.UNAUTHORIZED);
        }
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userid", user.getUserId());
        userInfo.put("name", user.getUserName());
        userInfo.put("email", user.getEmail());

        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK, "OK", userInfo), HttpStatus.OK);
    }
    @PostMapping("/user/create")
    public ResponseEntity<CommonResponse> userCreate(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        User user = userService.create(userCreateRequestDto);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED, "Sign Up Success!", user), HttpStatus.CREATED);
    }

    @PostMapping("/doLogin")
    public ResponseEntity<CommonResponse> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        User user = userService.login(loginRequestDto);

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()));
        String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(user.getUserId()));

        refreshTokenService.saveRefreshToken(String.valueOf(user.getUserId()), refreshToken);

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", user.getUserId());
        userInfo.put("token", accessToken);
        userInfo.put("refreshToken", refreshToken);

        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK, "Login Success!", userInfo), HttpStatus.OK);
    }

    @PostMapping("/doLogout")
    public ResponseEntity<CommonResponse> logout(@RequestBody Map<String, String> request) {
        String accessToken = request.get("token");
        Claims claims = jwtTokenProvider.getClaims(accessToken);
        String userId = claims.getSubject();
        refreshTokenService.deleteRefreshToken(userId);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK, "Logout Success", null), HttpStatus.OK);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<CommonResponse> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        emailService.sendOtpToEmail(email);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK, "OTP sent!", null), HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<CommonResponse> changePassword(@RequestBody ChangePasswordRequestDto dto) {
        userService.changePassword(dto);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK, "Password changed!", null), HttpStatus.OK);
    }
}