package com.goylik.libraryservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goylik.libraryservice.controller.config.LibraryControllerAbstractTest;
import com.goylik.libraryservice.model.dto.LibraryBookDto;
import com.goylik.libraryservice.model.dto.BookUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LibraryControllerPositiveTest extends LibraryControllerAbstractTest {
    @Test
    public void testVerifyBookAvailability() throws Exception {
        long bookId = 1L;

        when(libraryService.verifyBookAvailability(bookId)).thenReturn(true);

        mockMvc.perform(get("/api/library/1/availability").with(jwt()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
    @Test
    public void testUpdateBook() throws Exception {
        BookUpdateRequest bookDto = new BookUpdateRequest();
        bookDto.setBorrowedTime(LocalDateTime.now());
        bookDto.setReturnTime(LocalDateTime.now().plusDays(7));

        mockMvc.perform(put("/api/library/1")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Book was updated successfully."));
    }

    @Test
    public void testAddBook() throws Exception {
        LibraryBookDto bookDto = new LibraryBookDto();
        bookDto.setBookId(1L);

        mockMvc.perform(post("/api/library/1").with(jwt()))
                .andExpect(status().isOk())
                .andExpect(content().string("Book was added successfully."));
    }
}
