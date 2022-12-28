package com.grilledsausage.molva.api.entity.movie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findAllByIsInSurveyTrue();

    List<Movie> findAllByNameContains(String keyword);

    Optional<Movie> findByCode(Long code);

}
