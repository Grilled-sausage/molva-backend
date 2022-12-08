package com.grilledsausage.molva.api.dto.content;

import com.grilledsausage.molva.api.entity.movie.Movie;
import lombok.Builder;
import lombok.Data;

@Data
public class SurveyMovieResponseDto {

    Long id;

    Long code;

    String name;

    String image;

    @Builder
    public SurveyMovieResponseDto(Long id, Long code, String name, String image) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.image = image;
    }

    public static SurveyMovieResponseDto from(Movie movie) {
        return SurveyMovieResponseDto
                .builder()
                .id(movie.getId())
                .code(movie.getCode())
                .name(movie.getName())
                .image(movie.getImage())
                .build();
    }
}
