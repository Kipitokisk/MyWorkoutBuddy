package com.pweb.MyWorkoutBuddy.service;

import com.pweb.MyWorkoutBuddy.exception.ResourceNotFoundException;
import com.pweb.MyWorkoutBuddy.model.User;
import com.pweb.MyWorkoutBuddy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findUserByUsername(username);

            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("User with username: " + username + " not found.");
        }
    }
}
