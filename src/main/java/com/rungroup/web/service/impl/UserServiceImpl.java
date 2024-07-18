package com.rungroup.web.service.impl;

import com.rungroup.web.dto.UserDto;
import com.rungroup.web.mapper.UserMapper;
import com.rungroup.web.models.Role;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.repository.RoleRepository;
import com.rungroup.web.repository.UserRepository;
import com.rungroup.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.rungroup.web.mapper.UserMapper.mapUserToDto;
import static com.rungroup.web.mapper.UserMapper.mapUserToEntity;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        String encodedPasswd = passwordEncoder.encode(userDto.getPassword());
        UserEntity user = UserEntity
                .builder()
                .username(userDto.getUsername())
                .password(encodedPasswd)
                .email(userDto.getEmail())
                .build();
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRoles(Set.of(role));
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDto userDto) {
        UserEntity user = mapUserToEntity(userDto);
        userRepository.save(user);
    }

    // Updates user, but keeps the password
    @Override
    public UserDto updateUserWithoutPassword(UserDto userDto) {
        UserEntity entity = userRepository.findById(userDto.getId()).get();

        UserEntity user = mapUserToEntity(userDto);
        user.setPassword(entity.getPassword());

        return mapUserToDto(userRepository.save(user));
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserMapper::mapUserToDto).toList();
    }

    @Override
    public UserDto findById(Long userId) {
        UserEntity user = userRepository.findById(userId).get();
        return mapUserToDto(user);
    }
}
