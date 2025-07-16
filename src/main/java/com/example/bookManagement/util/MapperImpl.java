package com.example.bookManagement.util;

import com.example.bookManagement.dto.BookDTO;
import com.example.bookManagement.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class MapperImpl implements Mapper {

    @Override
    public BookDTO mapBookToBookDTO(Book book) {
        return new BookDTO(book.getId() ,book
                .getTitle(), book.getAuthor(), book.getPrice(), null);
    }

    @Override
    public Book mapBookDTOToBook(BookDTO bookDTO) {
        return new Book( bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getPrice());
    }


}
