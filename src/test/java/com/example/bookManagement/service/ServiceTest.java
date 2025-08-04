package com.example.bookManagement.service;

import com.example.bookManagement.dto.BookDTO;
import com.example.bookManagement.entity.Book;
import com.example.bookManagement.exception.BookNotFoundException;
import com.example.bookManagement.exception.DuplicateDataFoundException;
import com.example.bookManagement.exception.TitleFoundNullException;
import com.example.bookManagement.repository.BookRepo;
import com.example.bookManagement.util.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceTest {

    @Mock
    private BookRepo repository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private Service service;

    @Test
    void addBook() {
        BookDTO bookDTO = new BookDTO(0, "title", "author", 12, "");
        Book book = new Book("title", "author", 12);
        Book bookRepo = new Book("title", "author", 12);
        BookDTO bookDTOExpected = new BookDTO(0, "title", "author", 12, "");
        when(mapper.mapBookDTOToBook(bookDTO)).thenReturn(book);
        when(repository.save(book)).thenReturn(bookRepo);
        when(mapper.mapBookToBookDTO(bookRepo)).thenReturn(bookDTOExpected);
        BookDTO bookDTOResult = service.addBook(bookDTO);
        assertEquals(bookDTOExpected, bookDTOResult);
    }

    @Test
    void addBook_Error() {
        BookDTO bookDTO1 = new BookDTO(0, "", "author", 12, "");
        assertThrows(TitleFoundNullException.class, () -> service.addBook(bookDTO1));

        BookDTO bookDTO = new BookDTO(0, "title", "author", 12, "");
        Book book = new Book("title", "author", 12);
        when(mapper.mapBookDTOToBook(bookDTO)).thenReturn(book);
        when(repository.save(any(Book.class))).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateDataFoundException.class, () -> service.addBook(bookDTO));
    }

    @Test
    void getBook() {
        Book book = new Book("title", "author", 10);
        BookDTO bookDTOExpected = new BookDTO(0, "title", "author", 10, "");
        when(repository.getBookByTitle("title")).thenReturn(book);
        when(mapper.mapBookToBookDTO(book)).thenReturn(bookDTOExpected);
        BookDTO bookDTOActual = service.getBook("title");
        assertEquals(bookDTOExpected, bookDTOActual);
    }

    @Test
    void testGetBook_Error() {
        when(repository.getBookByTitle("title")).thenReturn(null);
        assertThrows(BookNotFoundException.class, () -> service.getBook("title"));
        assertThrows(TitleFoundNullException.class, () -> service.getBook(""));
    }

    @Test
    void deleteBook() {
        when(repository.deleteByTitle("title")).thenReturn(1);
        int actual = service.deleteBook("title");
        assertEquals(1, actual);
    }

    @Test
    void deleteBook_Error() {
        assertThrows(TitleFoundNullException.class, () -> service.deleteBook(""));
        when(repository.deleteByTitle("title")).thenReturn(0);
        assertThrows(BookNotFoundException.class, () -> service.deleteBook("title"));
    }

    @Test
    void updateBook() {
        BookDTO bookDTO = new BookDTO(0, "title", "author", 10, "");
        when(repository.updateTitleAndAuthorAndPriceById(any(String.class), any(String.class), any(Integer.class),
                                                         any(Integer.class)
        )).thenReturn(
                1);
        int actual = service.updateBook(bookDTO);
        assertEquals(1, actual);
    }

    @Test
    void updateBook_Error() {
        BookDTO bookDTO = new BookDTO(0, "", "author", 10, "");
        assertThrows(TitleFoundNullException.class, () -> service.updateBook(bookDTO));
        bookDTO.setTitle("title");
        when(repository.updateTitleAndAuthorAndPriceById(any(String.class), any(String.class), any(Integer.class),
                                                         any(Integer.class)
        )).thenReturn(
                0);
        assertThrows(RuntimeException.class, () -> service.updateBook(bookDTO));
        when(repository.updateTitleAndAuthorAndPriceById("title", "author", 10,
                                                         0
        )).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateDataFoundException.class, () -> service.updateBook(bookDTO));
    }

    @Test
    void getAllBooks() {
        Book book = new Book("title", "author", 10);
        BookDTO bookDTOExpected = new BookDTO(0, "title", "author", 10, "");
        List<Book> books = List.of(book);
        when(repository.findAll()).thenReturn(books);
        when(mapper.mapBooksToBookDTOs(books)).thenReturn(List.of(bookDTOExpected));
        List<BookDTO> actualBook = service.getAllBooks();
        assertEquals(bookDTOExpected, actualBook.get(0));
    }

    @Test
    void getAllBooks_Error() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(BookNotFoundException.class, () -> service.getAllBooks());
    }

    @Test
    void getBookByAuthor() {
        Book book = new Book("title", "author", 10);
        BookDTO bookDTOExpected = new BookDTO(0, "title", "author", 10, "");
        List<Book> bookList = List.of(book);
        when(repository.findAllByAuthor("title")).thenReturn(bookList);
        when(mapper.mapBookToBookDTO(book)).thenReturn(bookDTOExpected);
        List<BookDTO> bookActual = service.getBookByAuthor("title");
        assertEquals(bookDTOExpected, bookActual.get(0));
    }

    @Test
    void getBookByAuthor_Error() {
        assertThrows(TitleFoundNullException.class, () -> service.getBookByAuthor(""));
        when(repository.findAllByAuthor("title")).thenReturn(new ArrayList<>());
        assertThrows(BookNotFoundException.class, () -> service.getBookByAuthor("title"));
    }

    @Test
    void getByCriteria() {
        Book book = new Book("title", "author", 10);
        BookDTO bookDTOExpected = new BookDTO(0, "title", "author", 10, "");
        when(repository.findAllByCriteria("title", "author", 10)).thenReturn(List.of(book));
        when(mapper.mapBookToBookDTO(book)).thenReturn(bookDTOExpected);
        List<BookDTO> actualBookDto = service.getByCriteria("title", "author", "10");
        assertEquals(bookDTOExpected, actualBookDto.get(0));
    }

    @Test
    void getByCriteria_Error() {
        assertThrows(RuntimeException.class, () -> service.getByCriteria("", "", "c"));
    }
}