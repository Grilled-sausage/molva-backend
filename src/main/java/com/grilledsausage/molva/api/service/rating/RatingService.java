package com.grilledsausage.molva.api.service.rating;

import com.grilledsausage.molva.api.dto.rating.MovieRatingRequestDto;
import com.grilledsausage.molva.api.entity.movie.Movie;
import com.grilledsausage.molva.api.entity.movie.MovieRepository;
import com.grilledsausage.molva.api.entity.rating.Rating;
import com.grilledsausage.molva.api.entity.rating.RatingRepository;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.exception.custom.MovieNotFoundByIdException;
import com.grilledsausage.molva.exception.custom.RatingNotFoundByIdException;
import com.grilledsausage.molva.exception.custom.ValueOutOfBoundaryException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    private final MovieRepository movieRepository;

    @Transactional
    public Rating rateMovie(User user, MovieRatingRequestDto movieRatingRequestDto) {

        Long existingRatingId = getExistingRatingId(user, movieRatingRequestDto);

        validateValue(movieRatingRequestDto);

        if (existingRatingId == -1) {
            return makeNewRating(user, movieRatingRequestDto);
        } else {
            return updateExistingRating(movieRatingRequestDto, existingRatingId);
        }

    }

    public void validateValue(MovieRatingRequestDto movieRatingRequestDto) {

        if (movieRatingRequestDto.getMovieRating() > 5.0 || movieRatingRequestDto.getMovieRating() < 0.0) {
            throw ValueOutOfBoundaryException
                    .builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("유효하지 않은 값입니다. 평점에 0에서 5 사이의 실수를 입력해주세요.")
                    .build();
        }

    }

    public Long getExistingRatingId(User user, MovieRatingRequestDto movieRatingRequestDto) {

        Optional<Rating> ratingFromDto = ratingRepository.findByUser_IdAndMovie_Id(user.getId(), movieRatingRequestDto.getMovieId());

        if (ratingFromDto.isEmpty()) {
            return -1L;
        }

        return ratingFromDto.get().getId();

    }

    @Transactional
    public Rating makeNewRating(User user, MovieRatingRequestDto movieRatingRequestDto) {

        Movie movieFromDto = movieRepository
                .findById(movieRatingRequestDto.getMovieId())
                .orElseThrow(
                        () -> MovieNotFoundByIdException
                                .builder()
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .message("영화 ID를 통해 해당하는 영화를 찾을 수 없습니다.")
                                .build()
                );

        return ratingRepository.save(Rating.builder()
                .user(user)
                .movie(movieFromDto)
                .userRating(movieRatingRequestDto.getMovieRating())
                .build()
        );

    }

    @Transactional
    public Rating updateExistingRating(MovieRatingRequestDto movieRatingRequestDto, Long existingRatingId) {

        Rating existingRating = ratingRepository.findById(existingRatingId).orElseThrow(
                () -> RatingNotFoundByIdException
                        .builder()
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message("Rating의 id로 해당하는 Rating을 찾을 수 없습니다.")
                        .build()
        );

        existingRating.setUserRating(movieRatingRequestDto.getMovieRating());

        return ratingRepository.save(existingRating);

    }

}
