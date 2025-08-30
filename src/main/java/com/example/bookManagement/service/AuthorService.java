package com.example.bookManagement.service;

import com.example.bookManagement.entity.Author;
import com.example.bookManagement.entity.Book;
import com.example.bookManagement.entity.PublishBook;
import com.example.bookManagement.repository.AuthorRepo;
import com.example.bookManagement.repository.BookRepo;
import com.example.bookManagement.repository.PublishBookRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthorService {

    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;
    private final PublishBookRepo publishBookRepo;

    @Autowired
    public AuthorService(AuthorRepo authorRepo,
                         BookRepo bookRepo, PublishBookRepo publishBookRepo) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
        this.publishBookRepo = publishBookRepo;
    }

    public List<Author> getAuthorByName(String name) {
        List<Author> author = authorRepo.getAuthorByName(name);
        log.info("author with name: " + name + " and info : " + author);
        return author;
    }

    public Author addAuthor(Author author, Integer bookId) {
        Optional<Book> bookOptional = bookRepo.findById(bookId);
//        bookOptional.ifPresent(author::setBook);
        if (bookOptional.isPresent()) {
            author.setBook(bookOptional.get());
        } else {
            throw new RuntimeException("book id not found");
        }
        Author save = authorRepo.save(author);
//        Author result =  authorRepo.addAuthor(author);
        log.info("author added :" + save);
        return save;
    }


    public Author addAuthorPublishBookRelation(Long authorId, Long publishBookId) {
        Optional<Author> author = authorRepo.findById(authorId);
        Optional<PublishBook> publishBook = publishBookRepo.findById(publishBookId);
        if (author.isPresent() && publishBook.isPresent()) {
            author.get()
                  .getPublishBooks()
                  .add(publishBook.get());

            publishBook.get()
                       .getAuthors()
                       .add(author.get());

            publishBookRepo.save(publishBook.get());
            log.info("Set relation between author and publishBook is :" + author.get());
            return authorRepo.save(author.get());
        } else throw new RuntimeException("author or publishBook not found");
    }
}
