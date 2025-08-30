package com.example.bookManagement.controller;

import com.example.bookManagement.entity.PublishBook;
import com.example.bookManagement.service.PublishBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/PublishBook")
public class PublishBookController {

    private final PublishBookService publishBookService;

    @Autowired
    public PublishBookController(PublishBookService publishBookService) {
        this.publishBookService = publishBookService;
    }

    @GetMapping("/getById")
    public ResponseEntity<PublishBook> getPublishBook(@RequestParam Long id) {
        PublishBook publishBookById = publishBookService.getPublishBookById(id);
        return ResponseEntity.ok(publishBookById);
    }

    @PostMapping("/addById")
    public ResponseEntity<PublishBook> addPublishBook(@RequestBody PublishBook publishBook) {
        PublishBook book = publishBookService.addPublishBook(publishBook);
        return ResponseEntity.ok(book);
    }

}
