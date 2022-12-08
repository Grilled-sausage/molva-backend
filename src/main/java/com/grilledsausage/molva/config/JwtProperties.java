package com.grilledsausage.molva.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {

    public static final int EXPIRATION_TIME = 864000000;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    @Value("${jwt.secret}")
    public String SECRET;

}
