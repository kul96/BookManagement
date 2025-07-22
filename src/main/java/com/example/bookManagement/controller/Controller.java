package com.example.bookManagement.controller;

import com.example.bookManagement.dto.BookDTO;
import com.example.bookManagement.kafka.BookProducer;
import com.example.bookManagement.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final Service service;

    @Autowired
    private BookProducer bookProducer;

    @Value("${app.kafka.topic.my-topic}")
    private String topic;

    @Autowired
        // constructor inject
    Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/getByCriteria")
    public ResponseEntity<List<BookDTO>> getBookByCriteria(@RequestParam(required = false) String title,
                                                           @RequestParam(required = false) String author,
                                                           @RequestParam(required = false) String price) {
        logger.info("Received request for get book by criteria");
        var result = service.getByCriteria(title, author, price);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getBookByAuthor")
    public ResponseEntity<List<BookDTO>> getBookByAuthor(@RequestParam(required = false) String author) {
        logger.info("Received request for get book by author : {}", author);
        List<BookDTO> books = service.getBookByAuthor(author);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/page/getAllBooks")
    public ResponseEntity<Page<BookDTO>> pageGetAllBooks(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "2") int size) {
        logger.info("Received request for get all book by page.");
        Pageable pageable = PageRequest.of(page, size);
        Page<BookDTO> bookDTOS = service.pageGetAllBooks(pageable);
        return ResponseEntity.ok(bookDTOS);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
//        bookProducer.send(topic,"Kafka message sent on topic 'book-events' by kuldeep");
        logger.info("Received request for get all book.");
        List<BookDTO> allBooks = service.getAllBooks();
        bookProducer.send(topic,allBooks.get(0));
        return ResponseEntity.ok(allBooks);
    }

    @GetMapping("/getBook")
    public ResponseEntity<BookDTO> getBook(@RequestParam(required = false) String title) {
        logger.info("Received request for get book by title : {}", title);
        BookDTO bookDTO = service.getBook(title);
        return ResponseEntity.ok(bookDTO);
    }

    @PostMapping("/addBook")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        logger.info("Received request for add book ");
        BookDTO resultBookDTO = service.addBook(bookDTO);
        return ResponseEntity.ok(resultBookDTO);
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<String> deleteBook(@RequestParam(required = false) String title) {
        logger.info("Received request for delete book");
        var rows = service.deleteBook(title);
        return ResponseEntity.ok(" total " + rows + " row deleted ");
    }

    @PutMapping("/updateBook")
    public ResponseEntity<String> updateBook(@RequestBody BookDTO bookDTO) {
        logger.info("Received request for update book");
        int row = service.updateBook(bookDTO);
        return ResponseEntity.ok(row + " row updated with id " + bookDTO.getId());
    }

    //todo kafka + central exception(done) + logger(done) + update test
//


}
