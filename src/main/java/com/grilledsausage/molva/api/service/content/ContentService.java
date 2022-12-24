package com.grilledsausage.molva.api.service.content;

import com.grilledsausage.molva.api.dto.content.SurveyFilmmakerResponseDto;
import com.grilledsausage.molva.api.dto.content.SurveyMovieResponseDto;
import com.grilledsausage.molva.api.entity.filmmaker.Filmmaker;
import com.grilledsausage.molva.api.entity.filmmaker.FilmmakerRepository;
import com.grilledsausage.molva.api.entity.movie.Movie;
import com.grilledsausage.molva.api.entity.movie.MovieRepository;
import com.grilledsausage.molva.exception.custom.MovieNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ContentService {

    private final MovieRepository movieRepository;

    private final FilmmakerRepository filmmakerRepository;

    public List<SurveyMovieResponseDto> getSurveyMovieList() {

        List<Movie> movieList = movieRepository.findAllByIsInSurveyTrue();

        return movieList.stream().map(SurveyMovieResponseDto::from).collect(Collectors.toList());

    }

    public List<SurveyFilmmakerResponseDto> getSurveyFilmmakerList() {

        List<Filmmaker> filmmakerList = filmmakerRepository.findAllByIsInSurveyTrue();

        return filmmakerList.stream().map(SurveyFilmmakerResponseDto::from).collect(Collectors.toList());

    }


    public Movie getMovieInfo(Long movieId) {

        return movieRepository.findById(movieId).orElseThrow(
                () -> MovieNotFoundByIdException
                        .builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("주어진 movieId에 해당하는 영화가 없습니다.")
                        .build()
        );

    }
}
