package com.example.bookManagement.controller;

import com.example.bookManagement.dto.BookDTO;
import com.example.bookManagement.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class Controller {

    private final Service service;

    @Autowired
        // constructor inject
    Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/page/getAllBooks")
    public ResponseEntity<Page<BookDTO>> pageGetAllBooks(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookDTO> bookDTOS = service.pageGetAllBooks(pageable);
        return ResponseEntity.ok(bookDTOS);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> allBooks = service.getAllBooks();
        return ResponseEntity.ok(allBooks);
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
            if (row == 0) throw new Exception("Empty/wrong id");
            return ResponseEntity.ok(row + " row updated with id " + bookDTO.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(e.getMessage());
        }
    }

    //todo kafka + central exception + logger + update test
//


}
