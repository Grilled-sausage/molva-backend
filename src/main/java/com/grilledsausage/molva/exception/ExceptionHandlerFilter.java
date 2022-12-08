package com.grilledsausage.molva.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grilledsausage.molva.exception.custom.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            log.error("Unexpected Exception occurred: {}", e.getMessage(), e);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            ApiException apiException = ApiException
                    .builder()
                    .message(e.getMessage())
                    .httpStatus(e.getHttpStatus())
                    .timestamp(LocalDateTime.now())
                    .build();

            response.setStatus(e.getHttpStatus().value());
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            String result = objectMapper.writeValueAsString(apiException);
            response.getWriter().write(result);

        }
    }

}
