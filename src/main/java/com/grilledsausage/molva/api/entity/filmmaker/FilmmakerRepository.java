package com.grilledsausage.molva.api.entity.filmmaker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmmakerRepository extends JpaRepository<Filmmaker, Long> {

    List<Filmmaker> findAllByIsInSurveyTrue();

}
