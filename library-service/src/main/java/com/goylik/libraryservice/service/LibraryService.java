package com.goylik.libraryservice.service;

import com.goylik.libraryservice.model.dto.LibraryBookDto;
import com.goylik.libraryservice.model.dto.BookUpdateRequest;

import java.util.List;

public interface LibraryService {
    List<LibraryBookDto> getAvailableBooks();
    void updateBook(long bookId, BookUpdateRequest bookDto);
    void addBook(LibraryBookDto bookDto);
}
