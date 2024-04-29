package com.goylik.libraryservice.repository;

import com.goylik.libraryservice.model.entity.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<LibraryBook, Long> {
}
