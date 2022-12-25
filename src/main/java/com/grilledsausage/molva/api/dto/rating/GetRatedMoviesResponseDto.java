package com.grilledsausage.molva.api.dto.rating;

import com.grilledsausage.molva.api.entity.movie.Movie;
import lombok.Builder;
import lombok.Data;

@Data
public class GetRatedMoviesResponseDto {

    private String name;

    private String genreList;

    private String image;

    private Double rating;

    @Builder
    public GetRatedMoviesResponseDto(String name, String genreList, String image, Double rating) {
        this.name = name;
        this.genreList = genreList;
        this.image = image;
        this.rating = rating;
    }

    public static GetRatedMoviesResponseDto from(Movie movie, Double rating) {
        return GetRatedMoviesResponseDto
                .builder()
                .name(movie.getName())
                .genreList(movie.getGenreList())
                .image(movie.getImage())
                .rating(rating)
                .build();
    }

}
