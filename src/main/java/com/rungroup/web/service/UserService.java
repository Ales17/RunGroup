package com.rungroup.web.service;

import com.rungroup.web.dto.UserDto;
import com.rungroup.web.models.UserEntity;

public interface UserService {
    void saveUser(UserDto userDto);

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);
}
