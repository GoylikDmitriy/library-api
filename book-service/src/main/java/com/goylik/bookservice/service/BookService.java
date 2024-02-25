package com.goylik.bookservice.service;

import com.goylik.bookservice.model.dto.BookRequest;
import com.goylik.bookservice.model.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();
    BookDto getBookById(long id);
    BookDto getBookByIsbn(String isbn);
    BookDto addBook(BookRequest bookDto);
    void updateBook(long id, BookRequest bookDto);
    void deleteBookById(long id);
}
