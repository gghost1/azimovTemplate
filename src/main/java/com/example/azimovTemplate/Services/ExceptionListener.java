package com.example.azimovTemplate.Services;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionListener {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity hendlePasswordException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity(ex.getMessage(),HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity hendleBadCredentials(BadCredentialsException ex) {
        
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

}
