package com.example.bookManagement.util;

import com.example.bookManagement.dto.BookDTO;
import com.example.bookManagement.entity.Book;

import java.util.List;

public interface Mapper {
    public BookDTO mapBookToBookDTO(Book book);

    public Book mapBookDTOToBook(BookDTO bookDTO);

    public List<BookDTO> mapBooksToBookDTOs(List<Book> books);
}
