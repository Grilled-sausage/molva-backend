package com.grilledsausage.molva.api.service.rating;

import com.grilledsausage.molva.api.dto.rating.MovieRatingRequestDto;
import com.grilledsausage.molva.api.entity.movie.Movie;
import com.grilledsausage.molva.api.entity.movie.MovieRepository;
import com.grilledsausage.molva.api.entity.rating.Rating;
import com.grilledsausage.molva.api.entity.rating.RatingRepository;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.exception.custom.MovieNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    private final MovieRepository movieRepository;

    @Transactional
    public Rating rateMovie(User user, MovieRatingRequestDto movieRatingRequestDto) {

        Movie movieFromDto = movieRepository
                .findById(movieRatingRequestDto.getMovieId())
                .orElseThrow(
                        () -> MovieNotFoundByIdException
                                .builder()
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .message("영화 ID를 통해 해당하는 영화를 찾을 수 없습니다.")
                                .build()
                );

        return Rating.builder()
                .user(user)
                .movie(movieFromDto)
                .userRating(movieRatingRequestDto.getMovieRating())
                .build();

    }

}
