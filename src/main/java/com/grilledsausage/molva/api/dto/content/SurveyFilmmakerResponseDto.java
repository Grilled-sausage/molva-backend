package com.grilledsausage.molva.api.dto.content;

import com.grilledsausage.molva.api.entity.filmmaker.Filmmaker;
import lombok.Builder;
import lombok.Data;

@Data
public class SurveyFilmmakerResponseDto {

    private Long id;
    private Long code;

    private String name;

    private String type;

    private String image;

    private Boolean isInSurvey;

    @Builder
    public SurveyFilmmakerResponseDto(Long id, Long code, String name, String type, String image, Boolean isInSurvey) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.image = image;
        this.isInSurvey = isInSurvey;
    }

    public static SurveyFilmmakerResponseDto from(Filmmaker filmmaker) {

        return SurveyFilmmakerResponseDto
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
