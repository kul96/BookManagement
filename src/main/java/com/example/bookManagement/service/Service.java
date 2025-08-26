package com.example.bookManagement.service;

import com.example.bookManagement.dto.BookDTO;
import com.example.bookManagement.entity.Book;
import com.example.bookManagement.exception.BookNotFoundException;
import com.example.bookManagement.exception.DuplicateDataFoundException;
import com.example.bookManagement.exception.TitleFoundNullException;
import com.example.bookManagement.repository.BookRepo;
import com.example.bookManagement.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@org.springframework.stereotype.Service
@Slf4j
public class Service {
//    private static final Logger log = LoggerFactory.getLogger(Service.class);
// no need if @Slf4j is used . Auto generated same code at compile time

    private BookRepo repository;
    private Mapper mapper;

    @Autowired // no need to use this because there is only one constructor if more than one than use to remove ambiguity
        //Constructor inject
    Service(BookRepo repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public BookDTO addBook(BookDTO bookDTO) {
        log.info("Adding book with name: {} ", bookDTO.getTitle());
        if (bookDTO.getTitle() == null || bookDTO.getTitle()
                                                 .isBlank()) {
            throw new TitleFoundNullException("Value of Title should not be null/empty");
        }
        Book book = mapper.mapBookDTOToBook(bookDTO);
        Book save = null;
        try {
            save = repository.save(book);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateDataFoundException("Tile of Book '" + bookDTO.getTitle() + "' is Duplicate.");
        }
//        repository.addBook(book); // pass Book
        log.info("Book added with id : {}", save.getId());
        return mapper.mapBookToBookDTO(save);
    }

    public BookDTO getBook(String title) {
        log.info("Fetching book with name : {}", title);
        if (title == null || title.isBlank()) {
            throw new TitleFoundNullException("Title should not be empty");
        }
        Book book = repository.getBookByTitle(title);
        if (book == null) {
            throw new BookNotFoundException("Book with title '" + title + "' not found.");
        }
        return mapper.mapBookToBookDTO(book);
    }

    public int deleteBook(String title) {
        log.info("Deleting book with name : {}", title);
        if (title == null || title.isBlank()) {
            throw new TitleFoundNullException("Title should not be empty");
        }
            var row = repository.deleteByTitle(title);
            if(row == 0)
                throw new BookNotFoundException("Book with title '" + title + "' not found.");
            log.info("Book deleted with name : {}", title);
            return row;
    }

    /*
     * value: The name of the cache.
     * key: The key to store the value in the cache. You can use SpEL expressions (Spring Expression Language) here, like #id.
     */
//    @CachePut(value="cacheName", key = "#bookDTO.author")
    public int updateBook(BookDTO bookDTO) {
        log.info("Updating book with name : {}", bookDTO.getTitle());
        if (bookDTO.getTitle() == null || bookDTO.getTitle()
                                                 .isBlank()) {
            throw new TitleFoundNullException("Value of Title should not be null/empty");
        }
        try {
            var rows = repository.updateTitleAndAuthorAndPriceById(bookDTO.getTitle(),
                                                                   bookDTO.getAuthor(),
                                                                   bookDTO.getPrice(),
                                                                   bookDTO.getId()
            );
            if (rows == 0) { // todo dedicated exception
                throw new RuntimeException("Enter valid Data/ Data not match");
            }
            log.info("Book updated with name : {}", bookDTO.getTitle());
            return rows;
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateDataFoundException("Value of Title '" + bookDTO.getTitle() + "' is duplicate.");
        }
    }

    public List<BookDTO> getAllBooks() {
        log.info("Fetching all book.");
        List<Book> books = repository.findAll();
        if (books.isEmpty()) throw new BookNotFoundException("No Book found");
//        List<BookDTO> bookDTOS = books.stream()
//                                      .map(mapper::mapBookToBookDTO)
//                                      .toList();
        return mapper.mapBooksToBookDTOs(books);
    }

    public Page<BookDTO> pageGetAllBooks(Pageable pageable) {
        log.info("Fetching all book with page");
        Page<Book> bookPage = repository.findAll(pageable);
        if (bookPage.isEmpty()) throw new BookNotFoundException("No Book found");
        return bookPage.map(mapper::mapBookToBookDTO);
    }

    @Cacheable(value="cacheName", key = "#author")  //for update use @CachePut to update cache
    public List<BookDTO> getBookByAuthor(String author) {
        log.info("Fetching book with author : {}", author);
        if (author == null || author.isBlank()) {
            throw new TitleFoundNullException("Author should not be empty");
        }
        List<Book> allBookByAuthor = repository.findAllByAuthor(author);
        if (allBookByAuthor.isEmpty()) {
            throw new BookNotFoundException("Book with Author '" + author + "' not found.");
        }
        return allBookByAuthor.stream()
                              .map(mapper::mapBookToBookDTO)
                              .toList();
    }

    public List<BookDTO> getByCriteria(String title, String author, String price) {
        log.info("Fetching book with criteria  name :{} , author:{}, price:{}", title, author, price);
        Integer parsedPrice = null;
        try {
            if (price != null && !price.isBlank()) {
                parsedPrice = Integer.parseInt(price);
            }
        } catch (NumberFormatException exception) {
            throw new RuntimeException("price must be a valid Number"); // todo dedicated exception
        }
        List<Book> result = repository.findAllByCriteria(title, author, parsedPrice);
        if (result.size() == 0) throw new BookNotFoundException("No Book found");
//        return mapper.mapBooksToBookDTOs(result);
        return result.stream()
                     .map(book -> mapper.mapBookToBookDTO(book))
                     .toList();
    }
}
