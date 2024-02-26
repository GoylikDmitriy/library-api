package com.goylik.bookservice.client;

import com.goylik.bookservice.model.dto.BookClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "library-service")
public interface LibraryServiceClient {
    @PostMapping("/api/library/add")
    ResponseEntity<String> addBook(@RequestBody BookClientDto bookClientDto);
}
