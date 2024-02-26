package com.goylik.libraryservice.controller;

import com.goylik.libraryservice.model.dto.LibraryBookDto;
import com.goylik.libraryservice.model.dto.BookUpdateRequest;
import com.goylik.libraryservice.service.LibraryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@AllArgsConstructor
@Slf4j
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/available-books")
    public List<LibraryBookDto> getAvailableBooks() {
        log.info("[GET]: Getting all available books.");
        return libraryService.getAvailableBooks();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") long bookId,
                                             @RequestBody BookUpdateRequest bookDto) {
        log.info("[PUT]: Updating book with id = {}", bookId);

        libraryService.updateBook(bookId, bookDto);
        return ResponseEntity.ok("Book was updated successfully.");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody LibraryBookDto bookDto) {
        log.info("[POST]: Adding book to the library. Book = {}", bookDto);

        libraryService.addBook(bookDto);
        return ResponseEntity.ok("Book was added successfully.");
    }
}
