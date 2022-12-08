package com.grilledsausage.molva.exception.custom;

import org.springframework.http.HttpStatus;

public class NoJwtTokenException extends CustomException {

    public NoJwtTokenException(String message) {
        super(message);
    }

    public NoJwtTokenException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

}
