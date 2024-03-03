package com.goylik.bookservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {
    private String error;
    private int status;
    private Map<String, String> errors;
}
