package com.grilledsausage.molva.api.controller;

import com.grilledsausage.molva.api.dto.OAuthToken;
import com.grilledsausage.molva.api.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public OAuthToken getLogin(@RequestParam("code") String code) {

        return userService.getAccessToken(code);

    }
}
