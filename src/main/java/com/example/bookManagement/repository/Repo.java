package com.example.bookManagement.repository;

import com.example.bookManagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface Repo extends JpaRepository<Book, Integer> {
    @Query("select b from Book b where b.author = ?1")
    List<Book> findAllByAuthor(String author);
    @Transactional
    @Modifying
    @Query("delete from Book b where b.title = ?1")
    int deleteByTitle(String title);

    @Transactional
    @Modifying
    @Query("update Book b set b.title = ?1, b.author = ?2, b.price = ?3 where b.id = ?4")
    int updateTitleAndAuthorAndPriceById(String title, String author, Integer price, @NonNull Integer id);

    public Book getBookByTitle(String title);

}
