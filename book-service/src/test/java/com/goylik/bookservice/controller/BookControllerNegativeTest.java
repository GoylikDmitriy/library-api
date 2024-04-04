package com.goylik.bookservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goylik.bookservice.controller.config.BookControllerAbstractTest;
import com.goylik.bookservice.model.dto.BookRequest;
import com.goylik.bookservice.service.exception.BookNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerNegativeTest extends BookControllerAbstractTest {
    @Test
    public void testGetBookByIdBookNotFound() throws Exception {
        String exceptionMessage = "Book with id = 2 is not found.";
        when(bookService.getBookById(2L)).thenThrow(new BookNotFoundException(exceptionMessage));

        mockMvc.perform(get("/api/books/2").with(jwt()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", equalTo("Not Found")))
                .andExpect(jsonPath("$.status", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo(exceptionMessage)));
    }

    @Test
    public void testGetBookByIsbnBookNotFound() throws Exception {
        String isbn = "998-0-36-984519-2";
        String exceptionMsg = String.format("Book with ISBN = %s is not found.", isbn);
        when(bookService.getBookByIsbn(isbn))
                .thenThrow(new BookNotFoundException(exceptionMsg));

        mockMvc.perform(get("/api/books/isbn/" + isbn).with(jwt()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", equalTo("Not Found")))
                .andExpect(jsonPath("$.status", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo(exceptionMsg)));
    }

    @Test
    public void testUpdateBookNotFound() throws Exception {
        long bookId = 2L;
        BookRequest updateBookDto = new BookRequest();
        updateBookDto.setIsbn("978-3-16-148410-0");
        updateBookDto.setTitle("New Book Title");
        updateBookDto.setGenre("Science fiction");
        updateBookDto.setDescription("Book description about test and something else.");
        updateBookDto.setAuthor("New Author");

        String exceptionMsg = "Book with id = 2 is not found.";
        doThrow(new BookNotFoundException(exceptionMsg)).when(bookService).updateBook(bookId, updateBookDto);

        mockMvc.perform(put("/api/books/2")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateBookDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", equalTo("Not Found")))
                .andExpect(jsonPath("$.status", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo(exceptionMsg)));
    }

    @Test
    public void testDeleteBookNotFound() throws Exception {
        long bookId = 2L;
        String exceptionMsg = "Book with id = 2 is not found.";
        doThrow(new BookNotFoundException(exceptionMsg)).when(bookService).deleteBookById(bookId);
        mockMvc.perform(delete("/api/books/2").with(jwt()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", equalTo("Not Found")))
                .andExpect(jsonPath("$.status", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo(exceptionMsg)));
    }

    @Test
    public void testAddBookInvalidData() throws Exception {
        BookRequest invalidBookRequest = new BookRequest();
        invalidBookRequest.setIsbn("123");
        invalidBookRequest.setTitle("");
        invalidBookRequest.setGenre("Science fiction");
        invalidBookRequest.setDescription("Book description");
        invalidBookRequest.setAuthor("Author A.I.");

        mockMvc.perform(post("/api/books")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidBookRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateBookInvalidData() throws Exception {
        BookRequest invalidBookRequest = new BookRequest();
        invalidBookRequest.setIsbn("123");
        invalidBookRequest.setTitle("");
        invalidBookRequest.setGenre("Science fiction");
        invalidBookRequest.setDescription("Book description");
        invalidBookRequest.setAuthor("Author A.I.");

        mockMvc.perform(put("/api/books/1")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidBookRequest)))
                .andExpect(status().isBadRequest());
    }
}
