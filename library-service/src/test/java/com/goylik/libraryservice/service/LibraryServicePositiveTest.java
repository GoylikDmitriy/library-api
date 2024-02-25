package com.goylik.libraryservice.service;

import com.goylik.libraryservice.model.dto.LibraryBookDto;
import com.goylik.libraryservice.model.dto.BookUpdateRequest;
import com.goylik.libraryservice.model.entity.LibraryBook;
import com.goylik.libraryservice.repository.LibraryRepository;
import com.goylik.libraryservice.service.impl.LibraryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServicePositiveTest {
    @InjectMocks
    private LibraryServiceImpl libraryService;
    @Mock
    private LibraryRepository libraryRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testAddBook() {
        long bookId = 1L;
        LibraryBookDto bookDto = new LibraryBookDto();
        bookDto.setBookId(bookId);
        libraryService.addBook(bookDto);

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

    @Test
    public void testGetAvailableBooks() {
        LibraryBook book1 = new LibraryBook();
        book1.setBookId(1L);
        LibraryBook book2 = new LibraryBook();
        book2.setBookId(2L);
        List<LibraryBook> availableBooks = List.of(book1, book2);

        when(libraryRepository.findAllAvailableBooks()).thenReturn(availableBooks);

        LibraryBookDto response1 = new LibraryBookDto();
        response1.setBookId(book1.getBookId());
        LibraryBookDto response2 = new LibraryBookDto();
        response2.setBookId(book2.getBookId());

        when(modelMapper.map(book1, LibraryBookDto.class)).thenReturn(response1);
        when(modelMapper.map(book2, LibraryBookDto.class)).thenReturn(response2);

        List<LibraryBookDto> result = libraryService.getAvailableBooks();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getBookId());
        assertEquals(2L, result.get(1).getBookId());

        verify(libraryRepository, times(1)).findAllAvailableBooks();

        verify(modelMapper, times(1)).map(book1, LibraryBookDto.class);
        verify(modelMapper, times(1)).map(book2, LibraryBookDto.class);
    }
}
