package com.grilledsausage.molva.api.dto.rating;

import lombok.Data;

@Data
public class MovieRatingRequestDto {

    Long movieId;

    Double movieRating;

}
