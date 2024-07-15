package com.rungroup.web.security;

import com.rungroup.web.models.UserEntity;
import com.rungroup.web.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Ensure that we will get just one user
        UserEntity user = userRepository.findFirstByUsername(username);

        if (user != null) {
            return user;
        } else throw new UsernameNotFoundException("Invalid username or password");

    }
}
