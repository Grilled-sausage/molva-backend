package com.grilledsausage.molva.exception.custom;

import org.springframework.http.HttpStatus;

public class InvalidFilmmakerTypeValueException extends CustomException {

    public InvalidFilmmakerTypeValueException(String message) {
        super(message);
    }

    public InvalidFilmmakerTypeValueException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
