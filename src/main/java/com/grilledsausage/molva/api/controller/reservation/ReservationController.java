package com.grilledsausage.molva.api.controller.reservation;

import com.grilledsausage.molva.api.dto.reservation.MovieReservationResponseDto;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.api.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/movie")
    public ResponseEntity<MovieReservationResponseDto> reserveMovie(@AuthenticationPrincipal User user, @RequestBody Long movieId) {

        return ResponseEntity.ok(MovieReservationResponseDto.from(reservationService.reserveMovie(user, movieId)));
    }

    @DeleteMapping("/movie")
    public ResponseEntity<String> cancelReservingMovie(@AuthenticationPrincipal User user, @RequestBody Long movieId) {
        reservationService.cancelReservingMovie(user, movieId);

        return ResponseEntity.ok("success");
    }

}
