package com.grilledsausage.molva.exception.custom;

import org.springframework.http.HttpStatus;

public class InvalidJwtTokenException extends CustomException {

    public InvalidJwtTokenException(String message) {
        super(message);
    }

    public InvalidJwtTokenException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

}
