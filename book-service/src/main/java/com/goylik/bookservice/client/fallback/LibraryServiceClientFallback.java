package com.goylik.bookservice.client.fallback;

import com.goylik.bookservice.client.LibraryServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LibraryServiceClientFallback implements LibraryServiceClient {
    @Override
    public ResponseEntity<String> addBook(long bookId) {
        log.error("Can't get access to the library service. Book wasn't added to the library. Book id = {}", bookId);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Can't get access to the library service.");
    }

    @Override
    public ResponseEntity<Boolean> verifyBookAvailability(long bookId) {
        log.error("Can't get access to the library service. Can't verify book. Book id = {}", bookId);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
    }

    @Override
    public ResponseEntity<String> deleteBookById(long bookId) {
        log.error("Can't get access to the library service. Book can't be deleted in library service. Book id = {}", bookId);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Can't get access to the library service.");
    }
}
