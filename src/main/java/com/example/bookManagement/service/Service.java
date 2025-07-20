package com.example.bookManagement.service;

import com.example.bookManagement.dto.BookDTO;
import com.example.bookManagement.entity.Book;
import com.example.bookManagement.exception.BookNotFoundException;
import com.example.bookManagement.exception.DuplicateDataFoundException;
import com.example.bookManagement.exception.TitleFoundNullException;
import com.example.bookManagement.repository.Repo;
import com.example.bookManagement.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    private Repo repository;
    private Mapper mapper;

    @Autowired
        //Constructor inject
    Service(Repo repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public BookDTO addBook(BookDTO bookDTO) {
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
        return mapper.mapBookToBookDTO(save);
    }

    public BookDTO getBook(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title should not be empty");
        }
        Book book = repository.getBookByTitle(title);
        if (book == null) {
            throw new BookNotFoundException("Book with title '" + title + "' not found.");
        }
        return mapper.mapBookToBookDTO(book);
    }

    public int deleteBook(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title should not be empty");
        }
        try {
            return repository.deleteByTitle(title);
        } catch (EmptyResultDataAccessException exception) {
            throw new BookNotFoundException("Book with title '" + title + "' not found.");
        }
    }

    public int updateBook(BookDTO bookDTO) {
        if (bookDTO.getTitle() == null || bookDTO.getTitle()
                                                 .isBlank()) {
            throw new TitleFoundNullException("Value of Title should not be null/empty");
        }
        int rows = 0;
        try {
            rows = repository.updateTitleAndAuthorAndPriceById(bookDTO.getTitle(),
                                                               bookDTO.getAuthor(),
                                                               bookDTO.getPrice(),
                                                               bookDTO.getId()
            );
            if (rows == 0) { // todo dedicated exception
                throw new RuntimeException("Enter valid Data/ Data not match");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateDataFoundException("Value of Title '" + bookDTO.getTitle() + "' is duplicate.");
        }
        return rows;
    }

    public List<BookDTO> getAllBooks() {
        List<Book> books = repository.findAll();
        if (books.isEmpty()) throw new BookNotFoundException("No Book found");
//        List<BookDTO> bookDTOS = books.stream()
//                                      .map(mapper::mapBookToBookDTO)
//                                      .toList();
        return mapper.mapBooksToBookDTOs(books);
    }

    public Page<BookDTO> pageGetAllBooks(Pageable pageable) {
        Page<Book> bookPage = repository.findAll(pageable);
        if (bookPage.isEmpty()) throw new BookNotFoundException("No Book found");
        return bookPage.map(mapper::mapBookToBookDTO);
    }

    public List<BookDTO> getBookByAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author should not be empty");
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
