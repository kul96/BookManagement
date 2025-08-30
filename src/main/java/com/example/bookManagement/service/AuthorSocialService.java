package com.example.bookManagement.service;

import com.example.bookManagement.entity.Author;
import com.example.bookManagement.entity.AuthorSocial;
import com.example.bookManagement.repository.AuthorRepo;
import com.example.bookManagement.repository.AuthorSocialRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthorSocialService {
    private final AuthorSocialRepo authorSocialRepo;
    private final AuthorRepo authorRepo;

    @Autowired
    public AuthorSocialService(AuthorSocialRepo authorSocialRepo, AuthorRepo authorRepo) {
        this.authorSocialRepo = authorSocialRepo;
        this.authorRepo = authorRepo;
    }

    public AuthorSocial addAuthorSocial(AuthorSocial authorSocial, Long authorId) {
        Author author = authorRepo.findById(authorId)
                                  .orElseThrow(
                                          () -> new EntityNotFoundException(
                                                  "author with id:" + authorId + " not found "));
        authorSocial.setAuthor(author);
        return authorSocialRepo.save(authorSocial);
    }

    public AuthorSocial getAuthorSocialById(Long id) {
        AuthorSocial authorSocial = authorSocialRepo.findById(id)
                                                    .orElseThrow(() -> new EntityNotFoundException("not found"));
        log.info("Author social : " + authorSocial);
        return authorSocial;
    }
}
