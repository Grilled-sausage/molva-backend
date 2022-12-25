package com.grilledsausage.molva.config;

import com.grilledsausage.molva.api.service.user.UserService;
import com.grilledsausage.molva.exception.ExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProperties jwtProperties;
    private final UserService userService;
    private final CorsConfig corsConfig;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return web -> {
            web.ignoring().antMatchers(
                    "/swagger-ui.html",
                    "/h2-console/**",
                    "/api/auth/token"
            );
        };

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
//                .cors(AbstractHttpConfigurer::disable)
                .formLogin().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/token").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()
                .cacheControl().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .addFilter(corsConfig.corsFilter())
                .addFilterBefore(new JwtRequestFilter(jwtProperties, userService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(), JwtRequestFilter.class)
//                 .exceptionHandling()
//                 .authenticationEntryPoint()
//                 .accessDeniedHandler()
        ;

        return http.build();

    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://localhost:3000");
//        configuration.addAllowedHeader("*");
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//        configuration.addExposedHeader("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//
//    }
}
