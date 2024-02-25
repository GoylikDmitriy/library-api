package com.goylik.libraryservice.repository;

import com.goylik.libraryservice.model.entity.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibraryRepository extends JpaRepository<LibraryBook, Long> {
    @Query("SELECT lb FROM LibraryBook lb WHERE lb.borrowedTime IS NULL AND lb.returnTime IS NULL")
    List<LibraryBook> findAllAvailableBooks();
}
