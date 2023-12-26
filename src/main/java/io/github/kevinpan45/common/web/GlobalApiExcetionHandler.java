package io.github.kevinpan45.common.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.github.kevinpan45.common.exception.BasicException;
import io.github.kevinpan45.common.exception.BasicRuntimeException;

@ControllerAdvice
public class GlobalApiExcetionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> handleMultipartException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File upload error.");
    }

    @ExceptionHandler(BasicRuntimeException.class)
    public ResponseEntity<String> handleBusinessRuntimeError(BasicRuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(BasicException.class)
    public ResponseEntity<String> handleBusinessServerError(BasicException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleServerError(Exception e) {
        logger.error(SYSTEM_ERROR_MSG, e);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE.value()).body(SYSTEM_ERROR_MSG);
    }

    private static final String SYSTEM_ERROR_MSG = "System Internal error.";
}
