package com.grilledsausage.molva.api.service.content;

import com.grilledsausage.molva.api.dto.content.MovieInfoResponseDto;
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
                        .message("주어진 movieId에 해당하는 영화가 없습니다.")
                        .build()
        );

        // 1. movieId로 부터 movie를 받아와서 DTO로 가공.
        MovieInfoResponseDto movieInfoResponseDtoFromId = MovieInfoResponseDto.from(movieFromUserAndId);

        // 2. movieDto에 myRating 넣기
        movieInfoResponseDtoFromId.setMyRating(
                ratingRepository.findByUser_IdAndMovie_Id(
                        user.getId(), movieId).orElse(
                        Rating.builder().userRating(0.0).build()
                ).getUserRating()
        );

        // 3. movieDto에 reviewRating (평균) 넣기
        movieInfoResponseDtoFromId.setReviewRating(
                movieFromUserAndId
                        .getRatings().stream().map(Rating::getUserRating)
                        .mapToDouble(x -> x).summaryStatistics().getAverage()
        );

        // 4. movieDto에 감독 넣기
        movieInfoResponseDtoFromId.setDirectorInfoDto(
                MovieInfoResponseDto.FilmmakerInfoDto.from(movieFromUserAndId
                        .getParticipations().stream().map(Participation::getFilmmaker)
                        .filter(x -> x.getType().equals("감독")).findFirst().orElse(
                                Filmmaker
                                        .builder()
                                        .id(999L)
                                        .name("없음")
                                        .type("감독")
                                        .image("없음")
                                        .code(999L)
                                        .build()
                        )
                )
        );

        // 5. movieDto에 배우들 넣기
        List<MovieInfoResponseDto.FilmmakerInfoDto> actorInfoDtoList =
                movieFromUserAndId
                        .getParticipations().stream().map(Participation::getFilmmaker)
                        .filter(x -> x.getType().equals("배우")).map(MovieInfoResponseDto.FilmmakerInfoDto::from)
                        .collect(Collectors.toList());

        movieInfoResponseDtoFromId
                .setActorInfoDtoList(actorInfoDtoList.subList(0, 6));

        // 6. FilmmakerDto에 선호 여부 넣기
        movieInfoResponseDtoFromId.getDirectorInfoDto().setIsPreferred(
                user.getPreferences().stream().anyMatch(x -> x.getFilmmaker().getId().equals(movieInfoResponseDtoFromId.getDirectorInfoDto().getId()))
        );

        return movieInfoResponseDtoFromId;

    }
}
