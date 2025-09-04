package com.example.bookManagement.service;

import com.example.bookManagement.repository.UserSecurityDetailsRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserSecurityDetailsRepo userSecurityDetailsRepo;

    @Autowired
    public CustomUserDetailsService(UserSecurityDetailsRepo userSecurityDetailsRepo) {
        this.userSecurityDetailsRepo = userSecurityDetailsRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userSecurityDetailsRepo.findByUsername(username)
                                      .orElseThrow(
                                              () -> new UsernameNotFoundException(
                                                      "user with name: " + username + " not found"));
    }

}
