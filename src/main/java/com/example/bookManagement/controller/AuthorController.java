package com.example.bookManagement.controller;

import com.example.bookManagement.entity.Author;
import com.example.bookManagement.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/getAuthorByName")
    public ResponseEntity<List<Author>> getAuthorByName(@RequestParam String name) {
        List<Author> author = authorService.getAuthorByName(name);
        return ResponseEntity.ok(author);
    }

    @PostMapping("/addAuthor/{bookId}")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author, @PathVariable Integer bookId) {
        Author result = authorService.addAuthor(author, bookId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/addAuthorPublishBookRelation")
    public ResponseEntity<Author> addAuthorPublishBookRelation(@RequestParam Long authorId,
                                                               @RequestParam Long publishBookId) {
        Author author = authorService.addAuthorPublishBookRelation(authorId, publishBookId);
        return ResponseEntity.ok(author);
    }
}
