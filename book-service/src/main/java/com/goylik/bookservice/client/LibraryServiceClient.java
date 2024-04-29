package com.goylik.bookservice.client;

import com.goylik.bookservice.client.fallback.LibraryServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "library-service", fallback = LibraryServiceClientFallback.class)
public interface LibraryServiceClient {
    @PostMapping("/api/library/{id}")
    ResponseEntity<String> addBook(@PathVariable("id") long bookId);

    @GetMapping("/api/library/{id}/availability")
    ResponseEntity<Boolean> verifyBookAvailability(@PathVariable("id") long bookId);

    @DeleteMapping("/api/library/{id}")
    ResponseEntity<String> deleteBookById(@PathVariable("id") long bookId);
}
