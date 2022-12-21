package com.grilledsausage.molva.api.entity.rating;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByUser_IdAndMovie_Id(Long userId, Long movieId);

}
