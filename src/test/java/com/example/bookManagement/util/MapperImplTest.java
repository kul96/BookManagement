package com.example.bookManagement.util;

import com.example.bookManagement.dto.BookDTO;
import com.example.bookManagement.entity.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperImplTest {

    private final Mapper mapper = new MapperImpl();

    @Test
    void mapBookToBookDTO() {
        Book book = new Book("title1", "author1", 12);
        BookDTO bookDTO = mapper.mapBookToBookDTO(book);
        assertEquals(book.getTitle(), bookDTO.getTitle());
        assertEquals(book.getAuthor(), bookDTO.getAuthor());
        assertEquals(book.getPrice(), bookDTO.getPrice());
    }

    @Test
    void mapBookDTOToBook() {
        BookDTO bookDTO = new BookDTO(0, "title", "author", 12, "");
        Book book = mapper.mapBookDTOToBook(bookDTO);
        assertEquals(bookDTO.getTitle(), book.getTitle());
        assertEquals(bookDTO.getAuthor(), book.getAuthor());
        assertEquals(bookDTO.getPrice(), book.getPrice());
    }

    @Test
    void mapBooksToBookDTOs() {
        Book book1 = new Book("title1", "author1", 11);
        List<Book> book = List.of(book1);
        List<BookDTO> bookDTO = mapper.mapBooksToBookDTOs(book);
        assertEquals(book1.getTitle(), bookDTO.get(0)
                                              .getTitle());
        assertEquals(book1.getAuthor(), bookDTO.get(0)
                                               .getAuthor());
        assertEquals(book1.getPrice(), bookDTO.get(0)
                                              .getPrice());
    }
}