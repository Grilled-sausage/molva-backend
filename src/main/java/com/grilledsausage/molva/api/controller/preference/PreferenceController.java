package com.grilledsausage.molva.api.controller.preference;

import com.grilledsausage.molva.api.dto.preference.FilmmakerPreferenceResponseDto;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.api.service.preference.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/preference")
public class PreferenceController {

    private final PreferenceService preferenceService;

    @PostMapping("/filmmaker")
    public ResponseEntity<FilmmakerPreferenceResponseDto> preferFilmmaker(@AuthenticationPrincipal User user, @RequestBody Long filmmakerId) {

        return ResponseEntity.ok(FilmmakerPreferenceResponseDto.from(preferenceService.preferFilmmaker(user, filmmakerId)));

    }

    @DeleteMapping("/filmmaker")
    public ResponseEntity<String> cancelPreferringFilmmaker(@AuthenticationPrincipal User user, @RequestBody Long filmmakerId) {

        preferenceService.cancelPreferringFilmmaker(user, filmmakerId);

        return ResponseEntity.ok("success");

    }

}
