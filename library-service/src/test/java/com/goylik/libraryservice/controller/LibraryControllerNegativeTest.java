package com.goylik.libraryservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goylik.libraryservice.controller.config.LibraryControllerAbstractTest;
import com.goylik.libraryservice.model.dto.BookUpdateRequest;
import com.goylik.libraryservice.service.exception.BookNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LibraryControllerNegativeTest extends LibraryControllerAbstractTest {

    @Test
    public void testUpdateBookNotFound() throws Exception {
        long bookId = 2L;
        LocalDateTime borrowedTime = LocalDateTime.of(2024, 2, 25, 0, 0, 0);
        LocalDateTime returnTime = LocalDateTime.of(2024, 3, 7, 0, 0, 0);
        BookUpdateRequest bookDto = new BookUpdateRequest();
        bookDto.setBorrowedTime(borrowedTime);
        bookDto.setReturnTime(returnTime);

        String exceptionMsg = "No such book with id = 2 in library.";
        doThrow(new BookNotFoundException(exceptionMsg)).when(libraryService).updateBook(bookId, bookDto);

        mockMvc.perform(put("/api/library/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", equalTo("Not Found")))
                .andExpect(jsonPath("$.status", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo(exceptionMsg)));
    }

    @Test
    public void testUpdateBookInvalidPathVariable() throws Exception {
        BookUpdateRequest bookDto = new BookUpdateRequest();
        bookDto.setBorrowedTime(LocalDateTime.now());
        bookDto.setReturnTime(LocalDateTime.now().plusDays(7));

        mockMvc.perform(put("/api/library/invalid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", equalTo("Bad Request")))
                .andExpect(jsonPath("$.status", equalTo(400)));
    }
}
