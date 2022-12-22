package com.grilledsausage.molva.exception.custom;

import org.springframework.http.HttpStatus;

public class DuplicatedPreferenceException extends CustomException {
    public DuplicatedPreferenceException(String message) {
        super(message);
    }

    public DuplicatedPreferenceException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
