package com.goylik.bookservice.service;

import com.goylik.bookservice.model.dto.BookRequest;
import com.goylik.bookservice.model.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<BookDto> getBooks(Pageable pageable);
    BookDto getBookById(long id);
    BookDto getBookByIsbn(String isbn);
    BookDto addBook(BookRequest bookDto);
    void updateBook(long id, BookRequest bookDto);
    void deleteBookById(long id);
}
