package com.grilledsausage.molva.api.controller.rating;

import com.grilledsausage.molva.api.dto.rating.GetRatedMoviesResponseDto;
import com.grilledsausage.molva.api.dto.rating.MovieRatingRequestDto;
import com.grilledsausage.molva.api.dto.rating.MovieRatingResponseDto;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.api.service.rating.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/movie")
    public ResponseEntity<List<GetRatedMoviesResponseDto>> getRatedMovie(@AuthenticationPrincipal User user) {

        return ResponseEntity.ok(ratingService.getRatedMovie(user));
    }

    @PostMapping("/movie")
    public ResponseEntity<MovieRatingResponseDto> rateMovie(@AuthenticationPrincipal User user, @RequestBody MovieRatingRequestDto movieRatingRequestDto) {

        return ResponseEntity.ok(MovieRatingResponseDto.from(ratingService.rateMovie(user, movieRatingRequestDto)));

    }

    @DeleteMapping("/movie")
    public ResponseEntity<String> cancelRatingMovie(@AuthenticationPrincipal User user, @RequestBody Long movieId) {

        ratingService.cancelRatingMovie(user, movieId);

        return ResponseEntity.ok("success");

    }


}
