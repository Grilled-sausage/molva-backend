package com.grilledsausage.molva.api.dto.rating;

import com.grilledsausage.molva.api.entity.rating.Rating;
import lombok.Builder;
import lombok.Data;

@Data
public class MovieRatingResponseDto {

    private Long id;

    private Long code;

    private String name;

    private String image;

    private Double rating;

    @Builder
    public MovieRatingResponseDto(Long id, Long code, String name, String image, Double rating) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.image = image;
        this.rating = rating;
    }

    public static MovieRatingResponseDto from(Rating rating) {
        return MovieRatingResponseDto
                .builder()
                .id(rating.getMovie().getId())
                .code(rating.getMovie().getCode())
                .name(rating.getMovie().getName())
                .image(rating.getMovie().getImage())
                .rating(rating.getUserRating())
                .build();
    }

}
