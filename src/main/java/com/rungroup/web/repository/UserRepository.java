package com.rungroup.web.repository;

import com.rungroup.web.models.Role;
import com.rungroup.web.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    UserEntity findFirstByUsername(String username);

    List<UserEntity> findUserEntitiesByRolesNotContaining(Role role);
}
