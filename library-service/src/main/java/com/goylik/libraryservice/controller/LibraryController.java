package com.goylik.libraryservice.controller;

import com.goylik.libraryservice.model.dto.LibraryBookDto;
import com.goylik.libraryservice.model.dto.BookUpdateRequest;
import com.goylik.libraryservice.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@AllArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/available-books")
    public List<LibraryBookDto> getAvailableBooks() {
        return libraryService.getAvailableBooks();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") long bookId,
                                             @RequestBody BookUpdateRequest bookDto) {
        libraryService.updateBook(bookId, bookDto);
        return ResponseEntity.ok("Book was updated successfully.");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody LibraryBookDto bookDto) {
        libraryService.addBook(bookDto);
        return ResponseEntity.ok("Book was added successfully.");
    }
}
