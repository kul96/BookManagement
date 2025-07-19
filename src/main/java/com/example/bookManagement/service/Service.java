package com.example.bookManagement.service;

import com.example.bookManagement.dto.BookDTO;
import com.example.bookManagement.entity.Book;
import com.example.bookManagement.repository.Repo;
import com.example.bookManagement.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        Book book = mapper.mapBookDTOToBook(bookDTO);
//        repository.addBook(book); // pass Book
        Book save = repository.save(book);
        return mapper.mapBookToBookDTO(save);
    }
    public BookDTO getBook(String title) {

        Book book = repository.getBookByTitle(title);// return Book
        if(book == null){
            throw new RuntimeException("book not found");
        }
        return mapper.mapBookToBookDTO(book);
    }
    public int deleteBook(String title) {
            return repository.deleteByTitle(title);
    }
    public int updateBook(BookDTO bookDTO) {
//        Book book = mapper.mapBookDTOToBook(bookDTO);
        return repository.updateTitleAndAuthorAndPriceById(bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getPrice(),
                                                            bookDTO.getId()
        );
//        Book book1 = repository.update(book);
//         return mapper.mapBookToBookDTO(book1);
    }
    public List<BookDTO> getAllBooks() {
        List<Book> books = repository.findAll();
//        List<BookDTO> bookDTOS = books.stream()
//                                      .map(mapper::mapBookToBookDTO)
//                                      .toList();
        return mapper.mapBooksToBookDTOs(books);
    }

    public Page<BookDTO> pageGetAllBooks(Pageable pageable) {
        Page<Book> bookPage = repository.findAll(pageable);
        return bookPage.map(mapper::mapBookToBookDTO);
    }
    public List<BookDTO> getBookByAuthor(String author) {
        List<Book> allByAuthor = repository.findAllByAuthor(author);
        return allByAuthor.stream()
                          .map(mapper::mapBookToBookDTO)
                          .toList();
    }

    public List<BookDTO> getByCriteria(String title, String author, Integer price) {
        List<Book> result = repository.findAllByCriteria(title, author, price);
//        return mapper.mapBooksToBookDTOs(result);
        return result.stream()
                     .map(book -> mapper.mapBookToBookDTO(book))
                     .toList();
    }
}
