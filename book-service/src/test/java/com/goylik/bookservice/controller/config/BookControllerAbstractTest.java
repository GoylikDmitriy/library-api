package com.goylik.bookservice.controller.config;

import com.goylik.bookservice.client.LibraryServiceClient;
import com.goylik.bookservice.controller.BookController;
import com.goylik.bookservice.model.dto.BookDto;
import com.goylik.bookservice.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
public abstract class BookControllerAbstractTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected BookService bookService;
    @MockBean
    protected LibraryServiceClient libraryServiceClient;

    protected BookDto bookDto;

    @BeforeEach
    public void setup() {
        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setIsbn("978-3-16-148410-0");
        bookDto.setTitle("Book Title");
        bookDto.setGenre("Science fiction");
        bookDto.setDescription("Book description about test and something else.");
        bookDto.setAuthor("Author A.I.");
    }
}
