package io.github.TessTheCoder.ChikluAerospace.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerGlobal {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerGlobal.class);
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        logger.error("An error occurred: {}", e.getMessage(), e);
        return e.getMessage();
    }
}
