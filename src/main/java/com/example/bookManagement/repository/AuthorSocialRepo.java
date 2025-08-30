package com.example.bookManagement.repository;

import com.example.bookManagement.entity.AuthorSocial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorSocialRepo extends JpaRepository<AuthorSocial, Long> {

    Optional<AuthorSocial> findById(Long id);
}
