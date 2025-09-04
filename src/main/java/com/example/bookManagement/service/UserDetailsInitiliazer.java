package com.example.bookManagement.service;

import com.example.bookManagement.entity.UserSecurityDetails;
import com.example.bookManagement.repository.UserSecurityDetailsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UserDetailsInitiliazer {

    @Bean
    public CommandLineRunner createAdminUser(UserSecurityDetailsRepo userSecurityDetailsRepo,
                                             PasswordEncoder passwordEncoder) {
        return args -> {
            Optional<UserSecurityDetails> admin = userSecurityDetailsRepo.findByUsername("admin");
            if (admin.isEmpty()) {
                UserSecurityDetails userSecurityDetails = new UserSecurityDetails();
                userSecurityDetails.setUsername("admin");
                userSecurityDetails.setPassword(passwordEncoder.encode("admin123"));
                userSecurityDetails.setRole("ROLE_ADMIN");

                userSecurityDetailsRepo.save(userSecurityDetails);
                log.info("Default admin user created ");
            }
        };
    }
}
