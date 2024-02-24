package com.goylik.bookservice.service.config;

import com.goylik.bookservice.model.dto.BookDto;
import com.goylik.bookservice.model.entity.Book;
import com.goylik.bookservice.repository.BookRepository;
import com.goylik.bookservice.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class BookServiceAbstractTest {
    @InjectMocks
    protected BookServiceImpl bookService;
    @Mock
    protected BookRepository bookRepository;
    @Mock
    protected ModelMapper modelMapper;

    protected Book book;
    protected BookDto bookDto;
    protected final Pageable pageable = Pageable.ofSize(10);

    @BeforeEach
    public void setupBook() {
        book = new Book();
        book.setId(1L);
        book.setIsbn("978-3-16-148410-0");
        book.setTitle("Book Title");
        book.setGenre("Science fiction");
        book.setDescription("Book description about test and something else.");
        book.setAuthor("Author A.I.");
    }

    @BeforeEach
    public void setupBookDto() {
        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setIsbn("978-3-16-148410-0");
        bookDto.setTitle("Book Title");
        bookDto.setGenre("Science fiction");
        bookDto.setDescription("Book description about test and something else.");
        bookDto.setAuthor("Author A.I.");
    }
}
