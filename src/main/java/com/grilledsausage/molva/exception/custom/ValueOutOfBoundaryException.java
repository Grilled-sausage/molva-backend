package com.grilledsausage.molva.exception.custom;

import org.springframework.http.HttpStatus;

public class ValueOutOfBoundaryException extends CustomException {
    public ValueOutOfBoundaryException(String message) {
        super(message);
    }

    public ValueOutOfBoundaryException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
