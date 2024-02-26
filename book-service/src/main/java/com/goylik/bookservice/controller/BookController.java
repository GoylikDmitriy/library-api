package com.goylik.bookservice.controller;

import com.goylik.bookservice.client.LibraryServiceClient;
import com.goylik.bookservice.model.dto.BookClientDto;
import com.goylik.bookservice.model.dto.BookRequest;
import com.goylik.bookservice.model.dto.BookDto;
import com.goylik.bookservice.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
@Slf4j
public class BookController {
    private final LibraryServiceClient libraryServiceClient;
    private final BookService bookService;

    @GetMapping("/all")
    public List<BookDto> getAllBooks() {
        log.info("[GET]: Retrieving all books.");
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable("id") long bookId) {
        log.info("[GET]: Retrieving book with id = {}", bookId);
        return bookService.getBookById(bookId);
    }

    @GetMapping("/isbn/{isbn}")
    public BookDto getBookByIsbn(@PathVariable("isbn") String isbn) {
        log.info("[GET]: Retrieving book with ISBN = {}", isbn);
        return bookService.getBookByIsbn(isbn);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto addBook(@Valid @RequestBody BookRequest bookDto) {
        log.info("[POST]: Adding a new book: {}", bookDto);
        BookDto addedBook = bookService.addBook(bookDto);

        libraryServiceClient.addBook(new BookClientDto(addedBook.getId()));

        return addedBook;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") long bookId,
                                             @Valid @RequestBody BookRequest bookRequest) {
        log.info("[PUT]: Updating book with id = {}", bookId);
        bookService.updateBook(bookId, bookRequest);
        return ResponseEntity.ok("Book info was changed successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") long bookId) {
        log.info("[DELETE]: Deleting book with id = {}", bookId);
        bookService.deleteBookById(bookId);
        return ResponseEntity.ok("Book was deleted successfully.");
    }
}
