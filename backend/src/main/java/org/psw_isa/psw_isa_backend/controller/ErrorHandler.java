package org.psw_isa.psw_isa_backend.controller;

import org.psw_isa.psw_isa_backend.dtos.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception e, WebRequest request) {
        return new ResponseEntity<ErrorDetails>(new ErrorDetails(
            e.getMessage(), 
            request.getDescription(false)), 
        HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}