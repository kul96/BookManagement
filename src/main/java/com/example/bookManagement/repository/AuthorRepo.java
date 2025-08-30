package com.example.bookManagement.repository;

import com.example.bookManagement.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepo extends JpaRepository<Author, Long> {
    List<Author> getAuthorByName(String name);

//    Author addAuthor(Author author);
}
