package com.example.bookManagement.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //Handle Tile Found to null
    @ExceptionHandler(TitleFoundNullException.class)
    public ResponseEntity<ErrorResponse> handleTitleFoundNullException(TitleFoundNullException exception) {
        logger.warn("Name found empty/Blank : {}", exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(),
                                                        System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle Duplicate Data found exception
    @ExceptionHandler(DuplicateDataFoundException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateDataFoundException(DuplicateDataFoundException exception) {
        logger.warn("found duplicate Book : {}", exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(),
                                                        System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle BookNotFoundException
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFoundException(BookNotFoundException exception) {
        logger.warn("Book not found: {} ", exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(),
                                                        System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception exception) {
        logger.warn("Caught exception : {}", exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                                                        exception.getMessage(),
                                                        System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
