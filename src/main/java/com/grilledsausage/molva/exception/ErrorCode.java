package com.grilledsausage.molva.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(400),
    UNAUTHORIZED(400),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_ERROR(500),
    SERVICE_UNAVAILABLE(503),
    DB_ERROR(600);

    private final int status;

}
