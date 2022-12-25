package com.grilledsausage.molva.api.service.preference;


import com.grilledsausage.molva.api.dto.preference.GetPreferredFilmmakersResponseDto;
import com.grilledsausage.molva.api.entity.filmmaker.Filmmaker;
import com.grilledsausage.molva.api.entity.filmmaker.FilmmakerRepository;
import com.grilledsausage.molva.api.entity.preference.Preference;
import com.grilledsausage.molva.api.entity.preference.PreferenceRepository;
import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.exception.custom.DuplicatedPreferenceException;
import com.grilledsausage.molva.exception.custom.FilmmakerNotFoundByIdException;
import com.grilledsausage.molva.exception.custom.InvalidFilmmakerTypeValueException;
import com.grilledsausage.molva.exception.custom.PreferenceNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;

    private final FilmmakerRepository filmmakerRepository;

    @Transactional
    public Preference preferFilmmaker(User user, Long filmmakerId) {

        if (!isDuplicated(user, filmmakerId)) {
            return makeNewPreference(user, filmmakerId);
        } else {
            throw DuplicatedPreferenceException
                    .builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("이미 동일한 사용자가 동일한 영화인을 선호하고 있습니다.")
                    .build();
        }

    }

    private boolean isDuplicated(User user, Long filmmakerId) {

        return preferenceRepository.existsByUser_IdAndFilmmaker_Id(user.getId(), filmmakerId);

    }

    @Transactional
    protected Preference makeNewPreference(User user, Long filmmakerId) {

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

    @Transactional
    public void cancelPreferringFilmmaker(User user, Long filmmakerId) {

        Preference existingPreference = preferenceRepository
                .findByUser_IdAndFilmmaker_Id(user.getId(), filmmakerId)
                .orElseThrow(
                        () -> PreferenceNotFoundByIdException
                                .builder()
                                .httpStatus(HttpStatus.BAD_REQUEST)
                                .message("filmmakerId에 해당하는 영화인 선호 정보를 찾을 수 없습니다.")
                                .build()
                );

        preferenceRepository.deleteById(existingPreference.getId());

    }

    public List<GetPreferredFilmmakersResponseDto> getPreferredFilmmaker(User user, String type) {
        if (!type.equals("감독") && !type.equals("배우")) {
            throw InvalidFilmmakerTypeValueException
                    .builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("type의 값이 감독 또는 배우가 아닙니다.")
                    .build();
        }

        List<Preference> preferenceListFromUser = preferenceRepository.findAllByUser_Id(user.getId());
        preferenceListFromUser.removeIf(p -> !p.getFilmmaker().getType().equals(type));

        return preferenceListFromUser.stream().map(x -> GetPreferredFilmmakersResponseDto.from(x.getFilmmaker())).collect(Collectors.toList());
    }
}
