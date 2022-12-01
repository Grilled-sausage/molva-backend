package com.grilledsausage.molva.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidAuthorizationCodeException extends CustomException {

    public InvalidAuthorizationCodeException(String message) {
        super(message);
    }

    public InvalidAuthorizationCodeException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

}
