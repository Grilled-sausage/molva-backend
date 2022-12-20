package com.grilledsausage.molva.api.controller.preference;

import com.grilledsausage.molva.api.dto.preference.FilmmakerPreferenceResponseDto;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.api.service.preference.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/preference")
public class PreferenceController {

    private final PreferenceService preferenceService;

    @PostMapping("/filmmaker")
    public ResponseEntity<FilmmakerPreferenceResponseDto> preferFilmmaker(@AuthenticationPrincipal User user, @RequestBody Long filmmakerId) {

        return ResponseEntity.ok(FilmmakerPreferenceResponseDto.from(preferenceService.preferFilmmaker(user, filmmakerId)));

    }

}
