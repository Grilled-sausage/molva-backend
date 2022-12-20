package com.grilledsausage.molva.api.dto.preference;


import com.grilledsausage.molva.api.entity.filmmaker.Filmmaker;
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

    public static FilmmakerPreferenceResponseDto from(Filmmaker filmmaker) {
        return FilmmakerPreferenceResponseDto
                .builder()
                .id(filmmaker.getId())
                .code(filmmaker.getCode())
                .name(filmmaker.getName())
                .type(filmmaker.getType())
                .image(filmmaker.getImage())
                .isInSurvey(filmmaker.getIsInSurvey())
                .build();
    }

}
