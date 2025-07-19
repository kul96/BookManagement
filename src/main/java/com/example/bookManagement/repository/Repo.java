package com.example.bookManagement.repository;

import com.example.bookManagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface Repo extends JpaRepository<Book, Integer> {
//    @Query("select b from Book b where upper(b.title) = upper(?1) and upper(b.author) = upper(?2) and b.price = ?3")
//    @Query("SELECT b FROM Book b " +
//            "WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
//            "AND (:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) " +
//            "AND (:price IS NULL OR b.price = :price)")
    @Query("select b from Book b " +
            "where (?1 is NULL OR UPPER(b.title) Like UPPER(?1) ) " +
            "AND ( ?2 is NULL OR UPPER(b.author) Like UPPER(?2) ) " +
            "AND ( ?3 is Null OR b.price = ?3 )")
    List<Book> findAllByCriteria(String title, String author, Integer price);
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
