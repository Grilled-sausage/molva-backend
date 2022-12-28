package com.grilledsausage.molva.api.controller.content;

import com.grilledsausage.molva.api.dto.content.MovieInfoResponseDto;
import com.grilledsausage.molva.api.dto.content.SearchedMovieResponseDto;
import com.grilledsausage.molva.api.dto.content.SurveyFilmmakerResponseDto;
import com.grilledsausage.molva.api.dto.content.SurveyMovieResponseDto;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.api.service.content.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
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

    @GetMapping("/search")
    public ResponseEntity<List<SearchedMovieResponseDto>> searchByName(@RequestParam String keyword) {
        return ResponseEntity.ok(contentService.searchByName(keyword));
    }

    @GetMapping("/main")
    public ResponseEntity<List<SearchedMovieResponseDto>> getRecommendedMovies(@AuthenticationPrincipal User user, @RequestParam String genreName) {
        return ResponseEntity.ok(contentService.getRecommendedMovies(user, genreName));
    }

}
