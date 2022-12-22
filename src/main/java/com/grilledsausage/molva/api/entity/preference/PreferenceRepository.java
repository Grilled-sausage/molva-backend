package com.grilledsausage.molva.api.entity.preference;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {

    Optional<Preference> findByUser_IdAndFilmmaker_Id(Long userId, Long filmmaker_id);

    Boolean existsByUser_IdAndFilmmaker_Id(Long userId, Long filmmaker_id);

}
