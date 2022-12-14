package com.grilledsausage.molva.api.dto.content;

import com.grilledsausage.molva.api.entity.movie.Movie;
import lombok.Builder;
import lombok.Data;

@Data
public class SearchedMovieResponseDto {
    private Long id;

    private String name;

    private String genre;

    private String genreList;

    private Double rating;

    private String image;

    @Builder
    public SearchedMovieResponseDto(Long id, String name, String genre, String genreList, Double rating, String image) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.genreList = genreList;
        this.rating = rating;
        this.image = image;
    }

    public static SearchedMovieResponseDto from(Movie movie) {
        return SearchedMovieResponseDto
                .builder()
                .id(movie.getId())
                .name(movie.getName())
                .genre(movie.getGenre())
                .genreList(movie.getGenreList())
                .image(movie.getImage())
                .build();
    }
}
