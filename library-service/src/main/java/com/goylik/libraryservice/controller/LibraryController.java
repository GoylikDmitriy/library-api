package com.goylik.libraryservice.controller;

import com.goylik.libraryservice.model.dto.BookUpdateRequest;
import com.goylik.libraryservice.service.LibraryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library")
@AllArgsConstructor
@Slf4j
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> verifyBookAvailability(@PathVariable("id") long bookId) {
        log.info("[GET]: Verifying book availability.");
        boolean isAvailable = libraryService.verifyBookAvailability(bookId);
        return ResponseEntity.ok(isAvailable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") long bookId,
                                             @RequestBody BookUpdateRequest bookDto) {
        log.info("[PUT]: Updating book with id = {}", bookId);

        libraryService.updateBook(bookId, bookDto);
        return ResponseEntity.ok("Book was updated successfully.");
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> addBook(@PathVariable("id") long bookId) {
        log.info("[POST]: Adding book to the library. Book id = {}", bookId);
        libraryService.addBook(bookId);
        return ResponseEntity.ok("Book was added successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") long bookId) {
        log.info("[DELETE]: Deleting book from library. Book id = {}", bookId);
        libraryService.deleteBookById(bookId);
        return ResponseEntity.ok("Book was deleted successfully.");
    }
}
