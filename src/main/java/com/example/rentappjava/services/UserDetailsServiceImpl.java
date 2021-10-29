package com.example.rentappjava.services;


import com.example.rentappjava.entities.User;
import com.example.rentappjava.repos.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        User user = userOptional
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!userOptional.get().isEnabled()) {
            throw new RuntimeException("User must be enabled, check your email");
        }
        return new org.springframework.security
                .core.userdetails.User(user.getEmail(), user.getPassword(),
                user.isEnabled(), true, true,
                true, getAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
