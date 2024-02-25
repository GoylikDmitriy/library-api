package com.goylik.libraryservice.service;

import com.goylik.libraryservice.model.dto.BookUpdateRequest;
import com.goylik.libraryservice.model.entity.LibraryBook;
import com.goylik.libraryservice.repository.LibraryRepository;
import com.goylik.libraryservice.service.exception.BookNotFoundException;
import com.goylik.libraryservice.service.impl.LibraryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceNegativeTest {
    @InjectMocks
    private LibraryServiceImpl libraryService;
    @Mock
    private LibraryRepository libraryRepository;

    @Test
    public void testUpdateBookNotFound() {
        long bookId = 9999L;
        BookUpdateRequest bookDto = new BookUpdateRequest();
        bookDto.setBorrowedTime(LocalDateTime.now());
        bookDto.setReturnTime(LocalDateTime.now().plusDays(7));

        when(libraryRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> libraryService.updateBook(bookId, bookDto));

        verify(libraryRepository, times(1)).findById(bookId);
        verify(libraryRepository, never()).save(any(LibraryBook.class));
    }
}
