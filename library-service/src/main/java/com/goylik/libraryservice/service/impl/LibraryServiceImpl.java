package com.goylik.libraryservice.service.impl;

import com.goylik.libraryservice.model.dto.BookUpdateRequest;
import com.goylik.libraryservice.model.entity.LibraryBook;
import com.goylik.libraryservice.repository.LibraryRepository;
import com.goylik.libraryservice.service.LibraryService;
import com.goylik.libraryservice.service.exception.BookNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepository libraryRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean verifyBookAvailability(long bookId) {
        log.info("Verifying book availability. Book id = {}", bookId);
        LibraryBook book = fetchBookById(bookId);
        boolean isAvailable = book.getBorrowedTime() == null && book.getReturnTime() == null;

        log.info("Book with id = {} verified. Availability: {}", bookId, isAvailable);
        return isAvailable;
    }

    @Override
    @Transactional
    public void updateBook(long bookId, BookUpdateRequest bookDto) {
        log.info("Updating book with id = {}", bookId);

        LibraryBook book = fetchBookById(bookId);
        book.setBorrowedTime(bookDto.getBorrowedTime());
        book.setReturnTime(bookDto.getReturnTime());
        libraryRepository.save(book);

        log.info("Book was updated successfully.");
    }

    @Override
    @Transactional
    public void addBook(long bookId) {
        log.info("Adding book to the library. Book id = {}", bookId);

        LibraryBook libraryBook = new LibraryBook();
        libraryBook.setBookId(bookId);
        libraryRepository.save(libraryBook);

        log.info("Book was added to the library successfully.");
    }

    @Override
    @Transactional
    public void deleteBookById(long bookId) {
        log.info("Deleting book with id = {}", bookId);

        LibraryBook libraryBook = fetchBookById(bookId);
        libraryRepository.delete(libraryBook);

        log.info("Book with id = {} was deleted successfully.", bookId);
    }

    private LibraryBook fetchBookById(long bookId) {
        log.info("Fetching book in library with id = {}", bookId);
        return libraryRepository.findById(bookId)
                .orElseThrow(() -> {
                    log.error("Error while fetching book by id: Book with id = {} is not found.", bookId);
                    return new BookNotFoundException(String.format("No such book with id = %d in library.", bookId));
                });
    }
}
