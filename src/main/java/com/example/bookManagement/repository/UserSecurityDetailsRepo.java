package com.example.bookManagement.repository;

import com.example.bookManagement.entity.UserSecurityDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecurityDetailsRepo extends JpaRepository<UserSecurityDetails, Long> {
    Optional<UserSecurityDetails> findByUsername(String username);
}