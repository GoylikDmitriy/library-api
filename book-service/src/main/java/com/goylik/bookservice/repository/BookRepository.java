package com.goylik.bookservice.repository;

import com.goylik.bookservice.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByIsbn(String isbn);
}
