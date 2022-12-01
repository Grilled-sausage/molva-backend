package com.grilledsausage.molva.exception.custom;

import org.springframework.http.HttpStatus;

public class UserNotFoundByJwtException extends CustomException {

    public UserNotFoundByJwtException(String message) {
        super(message);
    }

    public UserNotFoundByJwtException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

}
