package com.example.bookManagement.controller;

import com.example.bookManagement.dto.BookDTO;
import com.example.bookManagement.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class Controller {

    private final Service service;

    @Autowired
        // constructor inject
    Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/getBook/{name}")
    public ResponseEntity<BookDTO> getBook(@PathVariable String name) {
        try {
            BookDTO bookDTO = service.getBook(name);
            return ResponseEntity.ok(bookDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                                 .body(new BookDTO(null, null, null, 0, e.getMessage()));
        }
    }

    @PostMapping("/addBook")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        BookDTO resultBookDTO = service.addBook(bookDTO);
        return ResponseEntity.ok(resultBookDTO);
    }

    @DeleteMapping("/deleteBook/{title}")
    public ResponseEntity<String> deleteBook(@PathVariable String title) {
        try {
            long rows = service.deleteBook(title);
            return ResponseEntity.ok(rows + " row deleted ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(e.getMessage());
        }
    }

    @PutMapping("/updateBook")
    public ResponseEntity<String> updateBook(@RequestBody BookDTO bookDTO) {
        try {
            int row = service.updateBook(bookDTO);
            return ResponseEntity.ok(row + " row updated with id " + bookDTO.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(e.getMessage());
        }
    }

    //todo kafka


}
