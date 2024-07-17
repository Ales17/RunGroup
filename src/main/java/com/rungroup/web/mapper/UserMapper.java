package com.rungroup.web.mapper;

import com.rungroup.web.dto.UserDto;
import com.rungroup.web.models.UserEntity;

public class UserMapper {
    public static UserDto mapUserToDto(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }

    public static UserEntity mapUserToEntity(UserDto user) {
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }
}
