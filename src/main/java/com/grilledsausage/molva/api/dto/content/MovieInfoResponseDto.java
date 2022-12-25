package com.grilledsausage.molva.api.dto.content;

import com.grilledsausage.molva.api.entity.filmmaker.Filmmaker;
import com.grilledsausage.molva.api.entity.movie.Movie;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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

    private Double myRating = 0.0;

    private Double naverRating;

    private Double reviewRating;

    private String story;

    private String image;

    private FilmmakerInfoDto directorInfoDto;

    private List<FilmmakerInfoDto> actorInfoDtoList;

    private Boolean isReserved = false;

    @Builder
    public MovieInfoResponseDto(Long id, Long code, String name, String englishName, Long year, String nation, String genre, String genreList, Long runTime, Double myRating, Double naverRating, Double reviewRating, String story, String image, Boolean isReserved) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.englishName = englishName;
        this.year = year;
        this.nation = nation;
        this.genre = genre;
        this.genreList = genreList;
        this.runTime = runTime;
        this.myRating = myRating;
        this.naverRating = naverRating;
        this.reviewRating = reviewRating;
        this.story = story;
        this.image = image;
        this.isReserved = isReserved;
    }

    @Data
    public static class FilmmakerInfoDto {
        private Long id;

        private String name;

        private String type;

        private String image;

        private Boolean isPreferred = false;

        @Builder
        public FilmmakerInfoDto(Long id, String name, String type, String image, Boolean isPreferred) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.image = image;
            this.isPreferred = isPreferred;
        }

        public static FilmmakerInfoDto from(Filmmaker filmmaker) {
            return FilmmakerInfoDto
                    .builder()
                    .id(filmmaker.getId())
                    .name(filmmaker.getName())
                    .type(filmmaker.getType())
                    .image(filmmaker.getImage())
                    .build();
        }
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
