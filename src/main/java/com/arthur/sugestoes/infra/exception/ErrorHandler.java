package com.arthur.sugestoes.infra.exception;

import com.arthur.sugestoes.infra.exception.error.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler (RuntimeException.class)
    public ResponseEntity<String> handler(RuntimeException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler (NotFoundException.class)
    public ResponseEntity<String> handler(NotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

}
