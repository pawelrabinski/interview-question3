package com.backbase.forum.exception;

import com.backbase.forum.dto.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    private static final String BAD_REQUEST_MSG = "Author and message fields are mandatory";
    private static final String UNKNOWN_ERROR_MSG = "Unknown error has occurred";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleBadRequestException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .errorCode(HttpStatus.BAD_REQUEST.toString())
                .errorMessage(BAD_REQUEST_MSG)
                .build(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleQuestionNotFoundException(QuestionNotFoundException e) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .errorCode(HttpStatus.NOT_FOUND.toString())
                .errorMessage(e.getMessage())
                .build(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleUnknownException(Exception e) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .errorMessage(UNKNOWN_ERROR_MSG).build(),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
