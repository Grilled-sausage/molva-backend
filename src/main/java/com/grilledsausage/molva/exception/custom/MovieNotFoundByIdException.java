package com.grilledsausage.molva.exception.custom;

import org.springframework.http.HttpStatus;

public class MovieNotFoundByIdException extends CustomException {

    public MovieNotFoundByIdException(String message) {
        super(message);
    }

    public MovieNotFoundByIdException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
