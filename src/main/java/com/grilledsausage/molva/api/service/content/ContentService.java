package com.grilledsausage.molva.api.service.content;

import com.grilledsausage.molva.api.dto.content.MovieInfoResponseDto;
import com.grilledsausage.molva.api.dto.content.SearchedMovieResponseDto;
import com.grilledsausage.molva.api.dto.content.SurveyFilmmakerResponseDto;
import com.grilledsausage.molva.api.dto.content.SurveyMovieResponseDto;
import com.grilledsausage.molva.api.entity.filmmaker.Filmmaker;
import com.grilledsausage.molva.api.entity.filmmaker.FilmmakerRepository;
import com.grilledsausage.molva.api.entity.movie.Movie;
import com.grilledsausage.molva.api.entity.movie.MovieRepository;
import com.grilledsausage.molva.api.entity.participation.Participation;
import com.grilledsausage.molva.api.entity.participation.ParticipationRepository;
import com.grilledsausage.molva.api.entity.preference.PreferenceRepository;
import com.grilledsausage.molva.api.entity.rating.Rating;
import com.grilledsausage.molva.api.entity.rating.RatingRepository;
import com.grilledsausage.molva.api.entity.reservation.ReservationRepository;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.exception.custom.CustomException;
import com.grilledsausage.molva.exception.custom.MovieNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ContentService {

    public static final URI
            FLASK_URI = UriComponentsBuilder
            .fromUriString("http://127.0.0.1:5000")
            .path("/recommend")
            .encode()
            .build()
            .toUri();

    private final MovieRepository movieRepository;

    private final FilmmakerRepository filmmakerRepository;

    private final RatingRepository ratingRepository;

    private final ReservationRepository reservationRepository;

    private final PreferenceRepository preferenceRepository;

    private final ParticipationRepository participationRepository;

    public List<SurveyMovieResponseDto> getSurveyMovieList() {

        List<Movie> movieList = movieRepository.findAllByIsInSurveyTrue();

        return movieList.stream().map(SurveyMovieResponseDto::from).collect(Collectors.toList());

    }

    public List<SurveyFilmmakerResponseDto> getSurveyFilmmakerList() {

        List<Filmmaker> filmmakerList = filmmakerRepository.findAllByIsInSurveyTrue();

        return filmmakerList.stream().map(SurveyFilmmakerResponseDto::from).collect(Collectors.toList());

    }


    public MovieInfoResponseDto getMovieInfo(User user, Long movieId) {

        Movie movieFromUserAndId = movieRepository.findById(movieId).orElseThrow(
                () -> MovieNotFoundByIdException
                        .builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("????????? movieId??? ???????????? ????????? ????????????.")
                        .build()
        );

        // 1. movieId??? ?????? movie??? ???????????? DTO??? ??????.
        MovieInfoResponseDto movieInfoResponseDtoFromId = MovieInfoResponseDto.from(movieFromUserAndId);

        // 2. movieDto??? myRating ??????
        movieInfoResponseDtoFromId.setMyRating(
                ratingRepository.findByUser_IdAndMovie_Id(
                        user.getId(), movieId).orElse(
                        Rating.builder().userRating(0.0).build()
                ).getUserRating()
        );

        // 3. movieDto??? reviewRating (??????) ??????
        movieInfoResponseDtoFromId.setReviewRating(
                movieFromUserAndId
                        .getRatings().stream().map(Rating::getUserRating)
                        .mapToDouble(x -> x).summaryStatistics().getAverage()
        );

        // 4. movieDto??? ?????? ??????
        movieInfoResponseDtoFromId.setDirectorInfoDto(
                MovieInfoResponseDto.FilmmakerInfoDto.from(movieFromUserAndId
                        .getParticipations().stream().map(Participation::getFilmmaker)
                        .filter(x -> x.getType().equals("??????")).findFirst().orElse(
                                null
                        )
                )
        );

        // 5. movieDto??? ????????? ??????
        List<MovieInfoResponseDto.FilmmakerInfoDto> actorInfoDtoList =
                movieFromUserAndId
                        .getParticipations().stream().map(Participation::getFilmmaker)
                        .filter(x -> x.getType().equals("??????")).map(MovieInfoResponseDto.FilmmakerInfoDto::from)
                        .collect(Collectors.toList());

        movieInfoResponseDtoFromId
                .setActorInfoDtoList(actorInfoDtoList.subList(0, Math.min(actorInfoDtoList.size(), 6)));

        // 6. FilmmakerDto??? ?????? ?????? ??????
        if (movieInfoResponseDtoFromId.getDirectorInfoDto() != null)
            movieInfoResponseDtoFromId.getDirectorInfoDto().setIsPreferred(
                    preferenceRepository.existsByUser_IdAndFilmmaker_Id(user.getId(), movieInfoResponseDtoFromId.getDirectorInfoDto().getId())
            );

        movieInfoResponseDtoFromId.getActorInfoDtoList().forEach(
                x -> x.setIsPreferred(preferenceRepository.existsByUser_IdAndFilmmaker_Id(user.getId(), x.getId()))
        );

        // 7. FilmmakerDto??? ??????????????? ?????? ??????
        movieInfoResponseDtoFromId.setIsReserved(reservationRepository.existsByUser_IdAndMovie_Id(user.getId(), movieId));

        return movieInfoResponseDtoFromId;

    }

    public List<SearchedMovieResponseDto> searchByName(String keyword) {
        List<SearchedMovieResponseDto> searchedMovieResponseDtoList =
                movieRepository.findAllByNameContains(keyword).stream().map(SearchedMovieResponseDto::from)
                        .collect(Collectors.toList());

        searchedMovieResponseDtoList.forEach(
                x -> x.setRating(
                        movieRepository.findById(x.getId()).orElseThrow(
                                        () -> MovieNotFoundByIdException
                                                .builder()
                                                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                                                .message("????????? PK??? ????????? ?????? ??? ????????????.")
                                                .build()
                                ).getRatings().stream().map(Rating::getUserRating)
                                .mapToDouble(val -> val).summaryStatistics().getAverage()
                )
        );

        return searchedMovieResponseDtoList;
    }

    public List<SearchedMovieResponseDto> getRecommendedMovies(User user) {

        List<Long> movieCodeList = ratingRepository.findAllByUser_Id(user.getId()).stream()
                .map(x -> x.getMovie().getCode()).collect(Collectors.toList());

        RestTemplate restTemplate = new RestTemplate();

        List<String> result = restTemplate.postForObject(FLASK_URI, movieCodeList, List.class);

        if (result == null) {
            throw CustomException
                    .builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Flask ????????? ?????? ??? ????????? ??????????????????.")
                    .build();
        }

        result = result.subList(0, 600);

//        log.info(result.toString());

        return result.stream()
                .map(x -> movieRepository.findByCode(Long.parseLong(x)).orElseThrow(
                        () -> MovieNotFoundByIdException
                                .builder()
                                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                                .message("????????? code??? ????????? ?????? ??? ????????????.")
                                .build())
                )
                .map(SearchedMovieResponseDto::from).collect(Collectors.toList());

    }
}
