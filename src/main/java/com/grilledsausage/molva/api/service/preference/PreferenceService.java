package com.grilledsausage.molva.api.service.preference;


import com.grilledsausage.molva.api.entity.filmmaker.Filmmaker;
import com.grilledsausage.molva.api.entity.filmmaker.FilmmakerRepository;
import com.grilledsausage.molva.api.entity.preference.Preference;
import com.grilledsausage.molva.api.entity.preference.PreferenceRepository;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.exception.custom.FilmmakerNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;

    private final FilmmakerRepository filmmakerRepository;

    @Transactional
    public Preference preferFilmmaker(User user, Long filmmakerId) {

        Filmmaker filmmakerFromId = filmmakerRepository
                .findById(filmmakerId)
                .orElseThrow(
                        () -> FilmmakerNotFoundByIdException
                                .builder()
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .message("영화인 ID를 통해 해당하는 영화인을 찾을 수 없습니다.")
                                .build()
                );

        return preferenceRepository.save(Preference.builder()
                .user(user)
                .filmmaker(filmmakerFromId)
                .build()
        );

    }

}
