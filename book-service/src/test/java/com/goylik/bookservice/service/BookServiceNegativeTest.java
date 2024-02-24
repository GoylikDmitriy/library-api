package com.goylik.bookservice.service;

import com.goylik.bookservice.model.dto.BookRequest;
import com.goylik.bookservice.model.entity.Book;
import com.goylik.bookservice.service.config.BookServiceAbstractTest;
import com.goylik.bookservice.service.exception.BookNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BookServiceNegativeTest extends BookServiceAbstractTest {
    @Test
    public void testGetBookByIdBookNotFound() {
        long id = 2L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(id));

        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    public void testGetBookByIsbnBookNotFound() {
        String isbn = "142-5-76-347411-8";
        when(bookRepository.findBookByIsbn(isbn)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookByIsbn(isbn));

        verify(bookRepository, times(1)).findBookByIsbn(isbn);
    }

    @Test
    public void testUpdateBookNotFound() {
        long bookId = 2L;
        BookRequest updateBookDto = new BookRequest();
        updateBookDto.setIsbn("978-3-16-148410-0");
        updateBookDto.setTitle("New Book Title");
        updateBookDto.setGenre("New Genre");
        updateBookDto.setDescription("Book description about test and something else.");
        updateBookDto.setAuthor("New Author");

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(bookId, updateBookDto));

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    public void testDeleteBookByIdBookNotFound() {
        long id = 2L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBookById(id));

        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, never()).delete(book);
    }
}
