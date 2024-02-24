package com.goylik.bookservice.service.impl;

import com.goylik.bookservice.model.dto.BookRequest;
import com.goylik.bookservice.model.dto.BookDto;
import com.goylik.bookservice.model.entity.Book;
import com.goylik.bookservice.repository.BookRepository;
import com.goylik.bookservice.service.BookService;
import com.goylik.bookservice.service.exception.BookNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BookDto> getBooks(Pageable pageable) {
        log.info("Getting all books by pages.");

        Page<Book> books = bookRepository.findAll(pageable);
        return books.map(b -> modelMapper.map(b, BookDto.class));
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

        return modelMapper.map(book, BookDto.class);
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

        log.info("Book with id = {} was deleted successfully.", id);
    }

    private Book fetchBookById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error while fetching book by id: Book with id = {} is not found.", id);
                    return new BookNotFoundException(String.format("Book with id = %d is not found.", id));
                });
    }
}
