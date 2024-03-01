package com.goylik.bookservice.client.fallback;

import com.goylik.bookservice.client.LibraryServiceClient;
import com.goylik.bookservice.model.dto.BookClientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LibraryServiceClientFallback implements LibraryServiceClient {
    @Override
    public ResponseEntity<String> addBook(BookClientDto bookClientDto) {
        log.error("Can't get access to the library service. Book wasn't added to the library. Book = {}", bookClientDto);
        return ResponseEntity.internalServerError().body("Can't get access to the library service.");
    }
}
