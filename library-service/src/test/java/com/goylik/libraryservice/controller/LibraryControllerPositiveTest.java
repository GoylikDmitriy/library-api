package com.goylik.libraryservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goylik.libraryservice.controller.config.LibraryControllerAbstractTest;
import com.goylik.libraryservice.model.dto.LibraryBookDto;
import com.goylik.libraryservice.model.dto.BookUpdateRequest;
import com.goylik.libraryservice.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LibraryControllerPositiveTest extends LibraryControllerAbstractTest {

    @Test
    public void testGetAllAvailableBooks() throws Exception {
        LibraryBookDto book1 = new LibraryBookDto();
        book1.setBookId(1L);
        LibraryBookDto book2 = new LibraryBookDto();
        book2.setBookId(2L);
        List<LibraryBookDto> availableBooks = List.of(book1, book2);

        when(libraryService.getAvailableBooks()).thenReturn(availableBooks);

        mockMvc.perform(get("/api/library/available-books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].book_id", equalTo((int) book1.getBookId())))
                .andExpect(jsonPath("$[1].book_id", equalTo((int) book2.getBookId())));
    }

    @Test
    public void testUpdateBook() throws Exception {
        BookUpdateRequest bookDto = new BookUpdateRequest();
        bookDto.setBorrowedTime(LocalDateTime.now());
        bookDto.setReturnTime(LocalDateTime.now().plusDays(7));

        mockMvc.perform(put("/api/library/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Book was updated successfully."));
    }

    @Test
    public void testAddBook() throws Exception {
        LibraryBookDto bookDto = new LibraryBookDto();
        bookDto.setBookId(1L);

        mockMvc.perform(post("/api/library/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Book was added successfully."));
    }
}
