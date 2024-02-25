package com.goylik.libraryservice.controller.advice;

import com.goylik.libraryservice.model.dto.ErrorResponse;
import com.goylik.libraryservice.service.exception.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class LibraryControllerAdvice {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleBookNotFoundException(BookNotFoundException ex) {
        log.error("[NOT FOUND]: {}.", ex.getMessage(), ex);
        return new ErrorResponse("Not Found", 404, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("[BAD REQUEST]: {}.", ex.getMessage(), ex);
        return new ErrorResponse("Bad Request", 400, "Invalid parameter type: " + ex.getName());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    public ErrorResponse handleDataAccessException(DataAccessException ex) {
        log.error("[INTERNAL SERVER ERROR]: {}.", ex.getMessage(), ex);
        return new ErrorResponse("Internal Server Error", 500,
                "An error occurred while accessing the data: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        log.error("[INTERNAL SERVER ERROR]: {}.", ex.getMessage(), ex);
        return new ErrorResponse("Internal Server Error", 500, ex.getMessage());
    }
}
