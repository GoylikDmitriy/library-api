package com.goylik.bookservice.service;

import com.goylik.bookservice.model.dto.BookRequest;
import com.goylik.bookservice.model.dto.BookDto;
import com.goylik.bookservice.model.entity.Book;
import com.goylik.bookservice.service.config.BookServiceAbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookServicePositiveTest extends BookServiceAbstractTest {
    @Test
    public void testGetAllBooks() {
        List<Book> books = Collections.singletonList(book);

        when(bookRepository.findAll()).thenReturn(books);
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        List<BookDto> result = bookService.getAllBooks();

        verify(bookRepository, times(1)).findAll();

        assertEquals(1, result.size());
        assertEquals(bookDto, result.get(0));
    }

    @Test
    public void testGetBookById() {
        long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        BookDto result = bookService.getBookById(id);

        verify(bookRepository, times(1)).findById(id);

        assertEquals(bookDto, result);
    }

    @Test
    public void testGetBookByIsbn() {
        String isbn = "978-3-16-148410-0";
        when(bookRepository.findBookByIsbn(isbn)).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        BookDto result = bookService.getBookByIsbn(isbn);

        verify(bookRepository, times(1)).findBookByIsbn(isbn);

        assertEquals(bookDto, result);
    }

    @Test
    public void testAddBook() {
        BookRequest addBookDto = new BookRequest();
        addBookDto.setIsbn("978-3-16-148410-0");
        addBookDto.setTitle("Book Title");
        addBookDto.setGenre("Science fiction");
        addBookDto.setDescription("Book description about test and something else.");
        addBookDto.setAuthor("Author A.I.");

        Book addBook = new Book();
        addBook.setIsbn("978-3-16-148410-0");
        addBook.setTitle("Book Title");
        addBook.setGenre("Science fiction");
        addBook.setDescription("Book description about test and something else.");
        addBook.setAuthor("Author A.I.");

        when(modelMapper.map(addBookDto, Book.class)).thenReturn(addBook);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        BookDto result = bookService.addBook(addBookDto);

        verify(bookRepository, times(1)).save(any(Book.class));

        assertEquals(bookDto, result);
    }

    @Test
    public void testUpdateBook() {
        long bookId = 1L;
        BookRequest updateBookDto = new BookRequest();
        updateBookDto.setIsbn("978-3-16-148410-0");
        updateBookDto.setTitle("New Book Title");
        updateBookDto.setGenre("New Genre");
        updateBookDto.setDescription("Book description about test and something else.");
        updateBookDto.setAuthor("New Author");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.updateBook(bookId, updateBookDto);

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(any(Book.class));

        assertEquals(bookId, book.getId());
        assertEquals(updateBookDto.getIsbn(), book.getIsbn());
        assertEquals(updateBookDto.getTitle(), book.getTitle());
        assertEquals(updateBookDto.getGenre(), book.getGenre());
        assertEquals(updateBookDto.getDescription(), book.getDescription());
        assertEquals(updateBookDto.getAuthor(), book.getAuthor());
    }

    @Test
    public void testDeleteBookById() {
        long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        bookService.deleteBookById(id);

        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(1)).delete(book);
    }
}
