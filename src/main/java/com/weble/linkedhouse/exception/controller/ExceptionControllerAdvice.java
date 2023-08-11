package com.weble.linkedhouse.exception.controller;

import com.weble.linkedhouse.exception.LinkedHouseException;
import com.weble.linkedhouse.exception.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(LinkedHouseException.class)
    public ResponseEntity<ErrorResponse> customException(LinkedHouseException e) {

        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        return ResponseEntity.status(statusCode)
                .body(body);
    }
}
