package com.grilledsausage.molva.exception;

import com.grilledsausage.molva.exception.custom.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ApiException> handleUserNotExistException(CustomException e) {

        HttpStatus httpStatus = e.getHttpStatus();

        ApiException apiException = ApiException
                .builder()
                .message(e.getMessage())
                .httpStatus(e.getHttpStatus())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiException, httpStatus);
    }
}
