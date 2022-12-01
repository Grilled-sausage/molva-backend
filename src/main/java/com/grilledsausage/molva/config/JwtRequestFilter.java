package com.grilledsausage.molva.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.api.service.UserService;
import com.grilledsausage.molva.exception.custom.InvalidJwtTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);

        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtHeader.replace(JwtProperties.TOKEN_PREFIX, "");

        String email = null;

        try {
            email = JWT.require(Algorithm.HMAC512(JWT_SECRET)).build().verify(token)
                    .getClaim("email").asString();

        } catch (TokenExpiredException e) {
            e.printStackTrace();
            throw InvalidJwtTokenException.builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("만료된 JWT입니다.")
                    .build();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            throw InvalidJwtTokenException.builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("유효하지 않은 JWT입니다.")
                    .build();
        }

        User user = userService.findByEmail(email);

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities()));

        filterChain.doFilter(request, response);
    }

}
