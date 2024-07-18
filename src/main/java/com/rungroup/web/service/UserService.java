package com.rungroup.web.service;

import com.rungroup.web.dto.UserDto;
import com.rungroup.web.models.UserEntity;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    void updateUser(UserDto userDto);

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    List<UserDto> findAll();

    UserDto findById(Long userId);
}
