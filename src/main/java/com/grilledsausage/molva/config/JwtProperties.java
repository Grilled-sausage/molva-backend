package com.grilledsausage.molva.config;

public interface JwtProperties {

    int EXPIRATION_TIME = 864000000;

    String TOKEN_PREFIX = "Bearer ";

    String HEADER_STRING = "Authorization";

}
