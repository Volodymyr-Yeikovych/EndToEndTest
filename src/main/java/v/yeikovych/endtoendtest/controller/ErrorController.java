package v.yeikovych.endtoendtest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> elementNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalOperation() {
        return ResponseEntity.badRequest().build();
    }
}
