package com.grilledsausage.molva.exception.custom;

import org.springframework.http.HttpStatus;

public class PreferenceNotFoundByIdException extends CustomException {
    public PreferenceNotFoundByIdException(String message) {
        super(message);
    }

    public PreferenceNotFoundByIdException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
