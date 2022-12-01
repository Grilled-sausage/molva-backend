package com.grilledsausage.molva.api.controller.user;

import com.grilledsausage.molva.api.dto.user.OAuthToken;
import com.grilledsausage.molva.api.dto.user.UserInfoResponseDto;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.api.service.UserService;
import com.grilledsausage.molva.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/auth/token")
    public ResponseEntity<String> getLogin(@RequestParam("code") String code) {

        OAuthToken oAuthToken = userService.getAccessToken(code);

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX
                + userService.saveUserAndGetToken(oAuthToken.getAccess_token()));

        return ResponseEntity.ok().headers(headers).body("");

    }

    @GetMapping("/user/info")
    public ResponseEntity<UserInfoResponseDto> getUserInfo(@AuthenticationPrincipal User user) {

        return ResponseEntity.ok().body(UserInfoResponseDto.from(user));

    }
}
