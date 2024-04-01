package com.example.book.repository;

import com.example.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {

    //custom query to find book by ISBN
    @Query(value = "SELECT * FROM books WHERE ISBN = ?1", nativeQuery = true)
    public Optional<Book> findByIsbn(String isbn);

    //custom query to delete book by ISBN
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM books WHERE isbn = ?1", nativeQuery = true)
    public void deleteByIsbn(@Param("isbn") String isbn);

    //custom query to check whether book exists in database or not using ISBN
    public boolean existsByIsbn(String isbn);

}
