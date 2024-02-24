package com.goylik.bookservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goylik.bookservice.controller.config.BookControllerAbstractTest;
import com.goylik.bookservice.model.dto.BookRequest;
import com.goylik.bookservice.model.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerPositiveTest extends BookControllerAbstractTest {
    @Test
    public void testGetBooks() throws Exception {
        Page<BookDto> bookPage = new PageImpl<>(Collections.singletonList(bookDto));
        when(bookService.getBooks(any(PageRequest.class))).thenReturn(bookPage);

        mockMvc.perform(get("/api/book/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id", equalTo((int) bookDto.getId())))
                .andExpect(jsonPath("$.content[0].isbn", equalTo(bookDto.getIsbn())))
                .andExpect(jsonPath("$.content[0].title", equalTo(bookDto.getTitle())))
                .andExpect(jsonPath("$.content[0].genre", equalTo(bookDto.getGenre())))
                .andExpect(jsonPath("$.content[0].description", equalTo(bookDto.getDescription())))
                .andExpect(jsonPath("$.content[0].author", equalTo(bookDto.getAuthor())));
    }

    @Test
    public void testGetBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(bookDto);

        mockMvc.perform(get("/api/book/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo((int) bookDto.getId())))
                .andExpect(jsonPath("$.isbn", equalTo(bookDto.getIsbn())))
                .andExpect(jsonPath("$.title", equalTo(bookDto.getTitle())))
                .andExpect(jsonPath("$.genre", equalTo(bookDto.getGenre())))
                .andExpect(jsonPath("$.description", equalTo(bookDto.getDescription())))
                .andExpect(jsonPath("$.author", equalTo(bookDto.getAuthor())));
    }

    @Test
    public void testGetBookByIsbn() throws Exception {
        when(bookService.getBookByIsbn("978-3-16-148410-0")).thenReturn(bookDto);

        mockMvc.perform(get("/api/book/isbn/978-3-16-148410-0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo((int) bookDto.getId())))
                .andExpect(jsonPath("$.isbn", equalTo(bookDto.getIsbn())))
                .andExpect(jsonPath("$.title", equalTo(bookDto.getTitle())))
                .andExpect(jsonPath("$.genre", equalTo(bookDto.getGenre())))
                .andExpect(jsonPath("$.description", equalTo(bookDto.getDescription())))
                .andExpect(jsonPath("$.author", equalTo(bookDto.getAuthor())));
    }

    @Test
    public void testAddBook() throws Exception {
        BookDto addBookDto = new BookDto();
        addBookDto.setIsbn("978-3-16-148410-0");
        addBookDto.setTitle("Book Title");
        addBookDto.setGenre("Science fiction");
        addBookDto.setDescription("Book description about test and something else.");
        addBookDto.setAuthor("Author A.I.");

        when(bookService.addBook(any(BookRequest.class))).thenReturn(bookDto);

        mockMvc.perform(post("/api/book/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addBookDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn", equalTo(addBookDto.getIsbn())))
                .andExpect(jsonPath("$.title", equalTo(addBookDto.getTitle())))
                .andExpect(jsonPath("$.genre", equalTo(addBookDto.getGenre())))
                .andExpect(jsonPath("$.description", equalTo(addBookDto.getDescription())))
                .andExpect(jsonPath("$.author", equalTo(addBookDto.getAuthor())));
    }

    @Test
    public void testUpdateBook() throws Exception {
        BookRequest updateBookDto = new BookRequest();
        updateBookDto.setIsbn("978-3-16-148410-0");
        updateBookDto.setTitle("New Book Title");
        updateBookDto.setGenre("Science fiction");
        updateBookDto.setDescription("Book description about test and something else.");
        updateBookDto.setAuthor("New Author");

        mockMvc.perform(put("/api/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateBookDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Book info was changed successfully."));
    }

    @Test
    public void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book was deleted successfully."));
    }
}