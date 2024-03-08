package com.example.azimovTemplate.Services;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionListener {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity hendlePasswordException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

}
