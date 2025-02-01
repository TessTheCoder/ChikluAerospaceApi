package io.github.TessTheCoder.ChikluAerospace.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerGlobal {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerGlobal.class);
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        logger.error("An error occurred: {}", e.getMessage(), e);
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("Validation error: {}", e.getMessage(), e);
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        logger.error("An error occurred: {}", e.getMessage(), e);
        return e.getMessage();
    }

}
