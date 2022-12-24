package com.grilledsausage.molva.api.controller.user;

import com.grilledsausage.molva.api.dto.user.OAuthToken;
import com.grilledsausage.molva.api.dto.user.UserInfoResponseDto;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.api.service.user.UserService;
import com.grilledsausage.molva.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/auth/token")
    public ResponseEntity<String> getLogin(@RequestParam("code") String code) {

        OAuthToken oAuthToken = userService.getAccessToken(code);
        log.info("AccessToken 발급 완료.");

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX
                + userService.saveUserAndGetToken(oAuthToken.getAccess_token()));
        log.info("User 저장 후 JWT 발급 완료.");


        return ResponseEntity.ok().headers(headers).body("success");

    }

    @GetMapping("/user/info")
    public ResponseEntity<UserInfoResponseDto> getUserInfo(@AuthenticationPrincipal User user) {

        return ResponseEntity.ok().body(UserInfoResponseDto.from(user));

    }

    @PutMapping("/user/info")
    public ResponseEntity<UserInfoResponseDto> putUserInfo(@AuthenticationPrincipal User user, @RequestBody String nickname) {

        return ResponseEntity.ok().body(UserInfoResponseDto.from(userService.putUserInfo(user, nickname)));

    }

    @DeleteMapping("/user/info")
    public ResponseEntity<UserInfoResponseDto> deleteUserInfo(@AuthenticationPrincipal User user) {

        userService.deleteUserInfo(user);

        return ResponseEntity.ok().body(UserInfoResponseDto.from(user));

    }

}
