package com.tauhoa.train.dtos.response;

import com.tauhoa.train.models.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {
    private String email;

    public static UserResponseDto toUserRequestDto(User user) {
        UserResponseDtoBuilder userResDtoBuilder = UserResponseDto.builder();
        userResDtoBuilder.email(user.getEmail());
        userResDtoBuilder.email(user.getUserName());
        return userResDtoBuilder.build();
    }
}
