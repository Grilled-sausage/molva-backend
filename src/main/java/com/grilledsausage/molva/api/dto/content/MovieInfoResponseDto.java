package com.grilledsausage.molva.api.dto.content;

import com.grilledsausage.molva.api.entity.movie.Movie;
import lombok.Builder;
import lombok.Data;

@Data
public class MovieInfoResponseDto {

    private Long id;

    private Long code;

    private String name;

    private String englishName;

    private Long year;

    private String nation;

    private String genre;

    private String genreList;

    private Long runTime;

    private Double naverRating;

    private Double reviewRating;

    private String story;

    private String image;

    @Builder
    public MovieInfoResponseDto(Long id, Long code, String name, String englishName, Long year, String nation, String genre, String genreList, Long runTime, Double naverRating, Double reviewRating, String story, String image) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.englishName = englishName;
        this.year = year;
        this.nation = nation;
        this.genre = genre;
        this.genreList = genreList;
        this.runTime = runTime;
        this.naverRating = naverRating;
        this.reviewRating = reviewRating;
        this.story = story;
        this.image = image;
    }

    public static MovieInfoResponseDto from(Movie movie) {
        return MovieInfoResponseDto
                .builder()
                .id(movie.getId())
                .code(movie.getCode())
                .name(movie.getName())
                .englishName(movie.getEnglishName())
                .year(movie.getYear())
                .nation(movie.getNation())
                .genre(movie.getGenre())
                .genreList(movie.getGenreList())
                .runTime(movie.getRunTime())
                .naverRating(movie.getNaverRating())
                .reviewRating(movie.getReviewRating())
                .story(movie.getStory())
                .image(movie.getImage())
                .build();
    }

}
