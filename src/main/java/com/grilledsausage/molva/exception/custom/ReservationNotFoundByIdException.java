package com.grilledsausage.molva.exception.custom;

import org.springframework.http.HttpStatus;

public class ReservationNotFoundByIdException extends CustomException {
    public ReservationNotFoundByIdException(String message) {
        super(message);
    }

    public ReservationNotFoundByIdException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
