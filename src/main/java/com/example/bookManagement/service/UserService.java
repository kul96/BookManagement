package com.example.bookManagement.service;

import com.example.bookManagement.entity.RegisterUserRequest;
import com.example.bookManagement.entity.UserSecurityDetails;
import com.example.bookManagement.repository.UserSecurityDetailsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserSecurityDetailsRepo repo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserSecurityDetailsRepo repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserSecurityDetails registerUser(RegisterUserRequest userRequest) {
        Optional<UserSecurityDetails> fetchUser = repo.findByUsername(userRequest.getUsername());
        if (fetchUser.isEmpty()) {
            UserSecurityDetails userSecurityDetails = new UserSecurityDetails();
            userSecurityDetails.setUsername(userRequest.getUsername());
            userSecurityDetails.setRole(userRequest.getRole());
            userSecurityDetails.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            UserSecurityDetails save = repo.save(userSecurityDetails);
            log.info("User created with user_name :" + userRequest.getUsername());
            return save;
        } else { //todo create custom exception
            throw new RuntimeException("User already exist");
        }
    }

    public List<UserSecurityDetails> getAllUser() {
        return repo.findAll();
    }
}
