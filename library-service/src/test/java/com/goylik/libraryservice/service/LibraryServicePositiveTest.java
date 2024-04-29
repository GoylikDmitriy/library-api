package com.goylik.libraryservice.service;

import com.goylik.libraryservice.model.dto.BookUpdateRequest;
import com.goylik.libraryservice.model.entity.LibraryBook;
import com.goylik.libraryservice.repository.LibraryRepository;
import com.goylik.libraryservice.service.impl.LibraryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServicePositiveTest {
    @InjectMocks
    private LibraryServiceImpl libraryService;
    @Mock
    private LibraryRepository libraryRepository;

    @Test
    public void testVerifyBookAvailabilityBookAvailable() {
        long bookId = 1L;
        LibraryBook libraryBook = new LibraryBook();
        libraryBook.setBookId(bookId);
        when(libraryRepository.findById(bookId)).thenReturn(Optional.of(libraryBook));

        boolean result = libraryService.verifyBookAvailability(bookId);

        verify(libraryRepository, times(1)).findById(bookId);

        assertTrue(result);
    }

    @Test
    public void testVerifyBookAvailabilityBookNotAvailable() {
        long bookId = 1L;
        LibraryBook libraryBook = new LibraryBook();
        libraryBook.setBookId(bookId);
        libraryBook.setBorrowedTime(LocalDateTime.MIN);
        libraryBook.setReturnTime(LocalDateTime.MAX);
        when(libraryRepository.findById(bookId)).thenReturn(Optional.of(libraryBook));

        boolean result = libraryService.verifyBookAvailability(bookId);

        verify(libraryRepository, times(1)).findById(bookId);

        assertFalse(result);
    }

    @Test
    public void testAddBook() {
        long bookId = 1L;
        libraryService.addBook(bookId);

        verify(libraryRepository, times(1)).save(any(LibraryBook.class));
    }

    @Test
    public void testUpdateBook() {
        long bookId = 1L;
        BookUpdateRequest bookDto = new BookUpdateRequest();
        bookDto.setBorrowedTime(LocalDateTime.now());
        bookDto.setReturnTime(LocalDateTime.now().plusDays(7));

        LibraryBook book = new LibraryBook();
        book.setBookId(bookId);
        when(libraryRepository.findById(bookId)).thenReturn(Optional.of(book));

        libraryService.updateBook(bookId, bookDto);

        verify(libraryRepository, times(1)).findById(bookId);

        assertEquals(bookDto.getBorrowedTime(), book.getBorrowedTime());
        assertEquals(bookDto.getReturnTime(), book.getReturnTime());

        verify(libraryRepository, times(1)).save(book);
    }
}
