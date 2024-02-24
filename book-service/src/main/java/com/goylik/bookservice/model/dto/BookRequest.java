package com.goylik.bookservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @NotBlank
    @Size(min = 10, max = 17)
    private String isbn;
    @NotBlank
    private String title;
    @NotBlank
    private String genre;
    @NotBlank
    private String description;
    @NotBlank
    private String author;
}
