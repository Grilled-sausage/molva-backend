package com.grilledsausage.molva.exception.custom;

import org.springframework.http.HttpStatus;

public class DuplicatedReservationException extends CustomException {

    public DuplicatedReservationException(String message) {
        super(message);
    }

    public DuplicatedReservationException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
