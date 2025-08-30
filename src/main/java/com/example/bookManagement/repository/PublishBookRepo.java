package com.example.bookManagement.repository;

import com.example.bookManagement.entity.PublishBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishBookRepo extends JpaRepository<PublishBook, Long> {
}
