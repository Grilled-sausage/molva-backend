package com.grilledsausage.molva.exception.custom;

import org.springframework.http.HttpStatus;

public class FilmmakerNotFoundByIdException extends CustomException {
    public FilmmakerNotFoundByIdException(String message) {
        super(message);
    }

    public FilmmakerNotFoundByIdException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
