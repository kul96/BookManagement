package com.example.bookManagement.util;

import com.example.bookManagement.dto.BookDTO;
import com.example.bookManagement.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapperImpl implements Mapper {
    private static final Logger logger = LoggerFactory.getLogger(MapperImpl.class);

    @Override
    public BookDTO mapBookToBookDTO(Book book) {
        logger.info("Converting Book to BookDTO for : {} ", book.getTitle());
        BookDTO bookDTO = new BookDTO(book.getId(), book
                .getTitle(), book.getAuthor(), book.getPrice(), null);
        logger.info("Converted Book to BookDTO: {}", bookDTO);
        return bookDTO;
    }

    @Override
    public Book mapBookDTOToBook(BookDTO bookDTO) {
        logger.info("Converting BookDTO to Book for : {}", bookDTO.getTitle());
        Book book = new Book(bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getPrice());
        logger.info("Converted BookDTO to Book: {}", book);
        return book;
    }

    @Override
    public List<BookDTO> mapBooksToBookDTOs(List<Book> books) {
//        var bookDTOList = new ArrayList<BookDTO>();
//        for (Book book : books) {
//            bookDTOList.add(mapBookToBookDTO(book));
//        }
        return books.stream()
                    .map(this::mapBookToBookDTO)
                    .toList();
    }
}
