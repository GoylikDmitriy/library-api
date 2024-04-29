package com.goylik.bookservice.service.impl;

import com.goylik.bookservice.client.LibraryServiceClient;
import com.goylik.bookservice.model.dto.BookRequest;
import com.goylik.bookservice.model.dto.BookDto;
import com.goylik.bookservice.model.entity.Book;
import com.goylik.bookservice.repository.BookRepository;
import com.goylik.bookservice.service.BookService;
import com.goylik.bookservice.service.exception.BookNotFoundException;
import com.goylik.bookservice.service.exception.ServiceUnavailableException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final LibraryServiceClient libraryServiceClient;
    private final ModelMapper modelMapper;

    @Override
    public List<BookDto> getAvailableBooks() {
        log.info("Getting available books.");

        List<BookDto> books = getAllBooks();
        List<BookDto> availableBooks = books.stream()
                .filter(this::verifyBookAvailability)
                .toList();

        log.info("Books were verified for availability.");
        return availableBooks;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        log.info("Getting all books.");

        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(b -> modelMapper.map(b, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getBookById(long id) {
        log.info("Getting book by id = {}", id);

        Book book = fetchBookById(id);
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getBookByIsbn(String isbn) {
        log.info("Getting book by ISBN = {}", isbn);

        Book book = bookRepository.findBookByIsbn(isbn)
                .orElseThrow(() -> {
                    log.error("Error while fetching book by ISBN: Book with ISBN = {} is not found.", isbn);
                    return new BookNotFoundException(String.format("Book with ISBN = %s is not found.", isbn));
                });

        return modelMapper.map(book, BookDto.class);
    }

    @Override
    @Transactional
    public BookDto addBook(BookRequest bookDto) {
        log.info("Adding book = {}", bookDto);

        Book book = modelMapper.map(bookDto, Book.class);
        book = bookRepository.save(book);
        log.info("Added book with id = {}", book.getId());

        BookDto addedBook = modelMapper.map(book, BookDto.class);
        addBookToLibrary(addedBook);
        return addedBook;
    }

    @Override
    @Transactional
    public void updateBook(long id, BookRequest bookDto) {
        log.info("Updating book with id = {}", id);

        Book book = fetchBookById(id);
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setGenre(bookDto.getGenre());
        book.setDescription(bookDto.getDescription());
        book.setAuthor(bookDto.getAuthor());

        bookRepository.save(book);

        log.info("Book with id = {} was updated successfully.", id);
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        log.info("Deleting book with id = {}", id);

        Book book = fetchBookById(id);
        bookRepository.delete(book);
        deleteBookByIdFromLibrary(id);

        log.info("Book with id = {} was deleted successfully.", id);
    }

    private Book fetchBookById(long id) {
        log.info("Fetching book by id = {}", id);
        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error while fetching book by id: Book with id = {} is not found.", id);
                    return new BookNotFoundException(String.format("Book with id = %d is not found.", id));
                });
    }

    private void addBookToLibrary(BookDto book) {
        log.info("Sending book id to the library-service. Book id = {}", book.getId());
        var response = libraryServiceClient.addBook(book.getId());
        if (response.getStatusCode().is5xxServerError()) {
            log.error("Error while trying to request with adding book to the library-service. " +
                    "Status code = {}", response.getStatusCode());
            throw new ServiceUnavailableException("Error while trying to request with adding book " +
                    "to the library-service. Status code = " + response.getStatusCode());
        }

        log.info("Book was added to the library.");
    }

    private void deleteBookByIdFromLibrary(long bookId) {
        log.info("Deleting book from the library-service. Book id = {}", bookId);
        var response = libraryServiceClient.deleteBookById(bookId);
        if (response.getStatusCode().is5xxServerError()) {
            log.error("Error while trying to request with deleting book from the library-service. " +
                    "Status code = {}", response.getStatusCode());
            throw new ServiceUnavailableException("Error while trying to request with deleting book " +
                    "from the library-service. Status code = " + response.getStatusCode());
        }

        log.info("Book was deleted from the library successfully.");
    }

    private boolean verifyBookAvailability(BookDto book) {
        log.info("Verifying book availability. Book id = {}", book.getId());
        var response = libraryServiceClient.verifyBookAvailability(book.getId());
        if (response.getStatusCode().is2xxSuccessful()) {
            Boolean isAvailable = response.getBody();
            log.info("Book with id = {} verified successfully.", book.getId());

            return Boolean.TRUE.equals(isAvailable);
        }
        else {
            log.error("Error while trying to request with verifying book availability to the library-service. " +
                    "Status code = {}", response.getStatusCode());
            throw new ServiceUnavailableException("Error while trying to request with verifying " +
                    "book availability to the library-service. Status code = " + response.getStatusCode());
        }
    }
}
