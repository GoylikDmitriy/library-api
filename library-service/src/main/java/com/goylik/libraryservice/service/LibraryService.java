package com.goylik.libraryservice.service;

import com.goylik.libraryservice.model.dto.BookUpdateRequest;

public interface LibraryService {
    boolean verifyBookAvailability(long bookId);
    void updateBook(long bookId, BookUpdateRequest bookDto);
    void addBook(long bookId);
    void deleteBookById(long bookId);
}
