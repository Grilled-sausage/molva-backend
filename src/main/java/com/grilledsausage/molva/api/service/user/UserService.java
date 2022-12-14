package com.grilledsausage.molva.api.service.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grilledsausage.molva.api.dto.user.KakaoProfile;
import com.grilledsausage.molva.api.dto.user.OAuthToken;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.api.entity.user.UserRepository;
import com.grilledsausage.molva.config.JwtProperties;
import com.grilledsausage.molva.exception.custom.InvalidAuthorizationCodeException;
import com.grilledsausage.molva.exception.custom.UserNotFoundByJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String CLIENT_SECRET;

    public OAuthToken getAccessToken(String code) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");
        headers.add("charset", "utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);
        params.add("client_secret", CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> accessTokenResponse = null;
        try {
            accessTokenResponse = rt.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    String.class
            );
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw InvalidAuthorizationCodeException
                    .builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("?????? ????????? ???????????? ????????????.")
                    .build();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(accessTokenResponse.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oAuthToken;
    }

    @Transactional
    public String saveUserAndGetToken(String token) {

        KakaoProfile profile = findProfile(token);

        Optional<User> user = userRepository.findByEmail(profile.getKakao_account().getEmail());

        if (user.isEmpty()) {
            User newUser = User.oAuth2Register()
                    .email(profile.getKakao_account().getEmail())
                    .password(passwordEncoder.encode(profile.getId().toString()))
                    .build();

            userRepository.save(newUser);

            return createToken(newUser);
        }

        return createToken(user.get());

    }

    public KakaoProfile findProfile(String token) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;

    }

    public String createToken(User user) {

        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("email", user.getEmail())
                .sign(Algorithm.HMAC512(JWT_SECRET));

    }

    @Transactional
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> UserNotFoundByJwtException
                                .builder()
                                .httpStatus(HttpStatus.UNAUTHORIZED)
                                .message("JWT ???????????? ???????????? ?????? ??? ????????????.")
                                .build()
                );
    }

    @Transactional
    public User putUserInfo(User user, String nickname) {

        user.setNickname(nickname);

        return userRepository.save(user);

    }

    @Transactional
    public void deleteUserInfo(User user) {

        userRepository.deleteUserByEmail(user.getEmail());

    }

}
