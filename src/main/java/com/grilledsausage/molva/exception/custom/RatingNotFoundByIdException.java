package com.grilledsausage.molva.exception.custom;

import org.springframework.http.HttpStatus;

public class RatingNotFoundByIdException extends CustomException {
    public RatingNotFoundByIdException(String message) {
        super(message);
    }

    public RatingNotFoundByIdException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
