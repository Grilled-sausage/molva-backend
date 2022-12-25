package com.grilledsausage.molva.api.entity.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByUser_IdAndMovie_Id(Long userId, Long movieId);

    Boolean existsByUser_IdAndMovie_Id(Long userId, Long movieId);

    List<Reservation> findAllByUser_Id(Long userId);

}
