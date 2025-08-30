package com.example.bookManagement.controller;

import com.example.bookManagement.entity.AuthorSocial;
import com.example.bookManagement.service.AuthorSocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authorSocial")
public class AuthorSocialController {

    private final AuthorSocialService authorSocialService;

    @Autowired
    public AuthorSocialController(AuthorSocialService authorSocialService) {
        this.authorSocialService = authorSocialService;
    }

    @PostMapping("/add/{authorId}")
    public ResponseEntity<AuthorSocial> addAuthorSocial(@RequestBody AuthorSocial authorSocial,
                                                        @PathVariable Long authorId) {
        AuthorSocial result = authorSocialService.addAuthorSocial(authorSocial, authorId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get")
    public ResponseEntity<AuthorSocial> getAuthorSocialById(@RequestParam Long id) {
        AuthorSocial resultList = authorSocialService.getAuthorSocialById(id);
        return ResponseEntity.ok(resultList);
    }


}
