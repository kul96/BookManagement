package com.example.bookManagement.util;

import com.example.bookManagement.dto.BookDTO;
import com.example.bookManagement.entity.Book;

public interface Mapper {
    public BookDTO mapBookToBookDTO(Book book);

    public Book mapBookDTOToBook(BookDTO bookDTO);
}
