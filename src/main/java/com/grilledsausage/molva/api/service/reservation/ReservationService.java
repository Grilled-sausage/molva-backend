package com.grilledsausage.molva.api.service.reservation;


import com.grilledsausage.molva.api.dto.reservation.GetReservedMoviesResponseDto;
import com.grilledsausage.molva.api.entity.movie.Movie;
import com.grilledsausage.molva.api.entity.movie.MovieRepository;
import com.grilledsausage.molva.api.entity.rating.Rating;
import com.grilledsausage.molva.api.entity.rating.RatingRepository;
import com.grilledsausage.molva.api.entity.reservation.Reservation;
import com.grilledsausage.molva.api.entity.reservation.ReservationRepository;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.exception.custom.DuplicatedReservationException;
import com.grilledsausage.molva.exception.custom.MovieNotFoundByIdException;
import com.grilledsausage.molva.exception.custom.ReservationNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final MovieRepository movieRepository;

    private final RatingRepository ratingRepository;

    @Transactional
    public Reservation reserveMovie(User user, Long movieId) {

        if (isDuplicated(user, movieId)) {
            throw DuplicatedReservationException
                    .builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("이미 동일한 사용자가 동일한 영화를 보고 싶어요 표시했습니다.")
                    .build();
        } else {
            return makeNewReservation(user, movieId);
        }

    }

    @Transactional
    protected Reservation makeNewReservation(User user, Long movieId) {

        Movie movieFromDto = movieRepository.findById(movieId).orElseThrow(
                () -> MovieNotFoundByIdException
                        .builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("주어진 movieID에 해당하는 영화를 찾을 수 없습니다.")
                        .build()
        );

        return reservationRepository.save(
                Reservation
                        .builder()
                        .movie(movieFromDto)
                        .user(user)
                        .build()
        );

    }

    private Boolean isDuplicated(User user, Long movieId) {
        return reservationRepository.existsByUser_IdAndMovie_Id(user.getId(), movieId);
    }

    @Transactional
    public void cancelReservingMovie(User user, Long movieId) {

        Reservation existingReservation = reservationRepository
                .findByUser_IdAndMovie_Id(user.getId(), movieId)
                .orElseThrow(
                        () -> ReservationNotFoundByIdException
                                .builder()
                                .httpStatus(HttpStatus.BAD_REQUEST)
                                .message("movieId에 해당하는 영화 보고싶어요 정보를 찾을 수 없습니다.")
                                .build()
                );

        reservationRepository.deleteById(existingReservation.getId());

    }

    public List<GetReservedMoviesResponseDto> getReservedMovie(User user) {
        List<Reservation> reservationListFromUser = reservationRepository.findAllByUser_Id(user.getId());

        List<GetReservedMoviesResponseDto> getReservedMoviesResponseDtoList
                = reservationListFromUser.stream()
                .map(x -> GetReservedMoviesResponseDto.from(x.getMovie(), 0.0))
                .collect(Collectors.toList());

        getReservedMoviesResponseDtoList.forEach(
                x -> x.setRating(
                        ratingRepository.findByUser_IdAndMovie_Id(
                                user.getId(), x.getId()
                        ).orElse(
                                Rating.builder().userRating(0.0).build()
                        ).getUserRating()
                )
        );

        return getReservedMoviesResponseDtoList;
    }
}
