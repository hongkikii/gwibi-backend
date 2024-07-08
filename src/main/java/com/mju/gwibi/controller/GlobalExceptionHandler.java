package com.mju.gwibi.controller;

import com.mju.gwibi.dto.response.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotfoundException() {
        List<String> errors = new ArrayList<>();
        errors.add("id에 해당하는 데이터가 존재하지 않습니다");
        return makeErrorResponse(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream()
                .map(fieldError ->
                        fieldError.getField() + ", " + fieldError.getDefaultMessage())
                .toList();
        return makeErrorResponse(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        List<String> errors = new ArrayList<>();
        errors.add(e.getMessage());
        return makeErrorResponse(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> makeErrorResponse(List<String> errors, HttpStatus status) {
        return ResponseEntity.status(status)
                        .body(new ErrorResponse(errors));
    }
}
