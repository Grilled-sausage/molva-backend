package com.grilledsausage.molva.api.dto.reservation;

import com.grilledsausage.molva.api.entity.movie.Movie;
import lombok.Builder;
import lombok.Data;

@Data
public class GetReservedMoviesResponseDto {

    private Long id;

    private String name;

    private String genreList;

    private String image;

    private Double rating;

    @Builder
    public GetReservedMoviesResponseDto(Long id, String name, String genreList, String image, Double rating) {
        this.id = id;
        this.name = name;
        this.genreList = genreList;
        this.image = image;
        this.rating = rating;
    }

    public static GetReservedMoviesResponseDto from(Movie movie, Double rating) {
        return GetReservedMoviesResponseDto
                .builder()
                .id(movie.getId())
                .name(movie.getName())
                .genreList(movie.getGenreList())
                .image(movie.getImage())
                .rating(rating)
                .build();
    }

}

