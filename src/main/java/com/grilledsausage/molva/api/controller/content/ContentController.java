package com.grilledsausage.molva.api.controller.content;

import com.grilledsausage.molva.api.dto.content.MovieInfoResponseDto;
import com.grilledsausage.molva.api.dto.content.SurveyFilmmakerResponseDto;
import com.grilledsausage.molva.api.dto.content.SurveyMovieResponseDto;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.api.service.content.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/content")
public class ContentController {

    private final ContentService contentService;

    @GetMapping("/survey/movie")
    public List<SurveyMovieResponseDto> getSurveyMovieList() {

        return contentService.getSurveyMovieList();
    }

    @GetMapping("/survey/filmmaker")
    public List<SurveyFilmmakerResponseDto> getSurveyFilmmakerList() {

        return contentService.getSurveyFilmmakerList();
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieInfoResponseDto> getMovieInfo(@AuthenticationPrincipal User user, @PathVariable Long movieId) {

        return ResponseEntity.ok(contentService.getMovieInfo(user, movieId));
    }

}
