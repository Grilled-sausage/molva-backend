package com.grilledsausage.molva.api.dto.preference;


import com.grilledsausage.molva.api.entity.preference.Preference;
import lombok.Builder;
import lombok.Data;

@Data
public class FilmmakerPreferenceResponseDto {

    private Long id;

    private Long code;

    private String name;

    private String type;

    private String image;

    private Boolean isInSurvey;

    @Builder
    public FilmmakerPreferenceResponseDto(Long id, Long code, String name, String type, String image, Boolean isInSurvey) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.image = image;
        this.isInSurvey = isInSurvey;
    }

    public static FilmmakerPreferenceResponseDto from(Preference preference) {
        return FilmmakerPreferenceResponseDto
                .builder()
                .id(preference.getFilmmaker().getId())
                .code(preference.getFilmmaker().getCode())
                .name(preference.getFilmmaker().getName())
                .type(preference.getFilmmaker().getType())
                .image(preference.getFilmmaker().getImage())
                .isInSurvey(preference.getFilmmaker().getIsInSurvey())
                .build();
    }

}
