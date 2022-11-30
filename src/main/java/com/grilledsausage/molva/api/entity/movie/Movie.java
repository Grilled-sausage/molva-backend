package com.grilledsausage.molva.api.entity.movie;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "Movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private Long code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_en")
    private String englishName;

    @Column(name = "year")
    private Long year;

    @Column(name = "nation")
    private String nation;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "genre_list", nullable = false)
    private String genreList;

    @Column(name = "run_time")
    private Long runTime;

    @Column(name = "naver_rating")
    private Double naverRating;

    @Column(name = "review_rating")
    private Double reviewRating;

    @Column(name = "story", columnDefinition = "TEXT")
    private String story;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

//    양방향 조회가 필요할 때 추가하기
//    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
//    private List<Reservation> reservations = new ArrayList<Reservation>();

    @Builder
    public Movie(Long code, String name, String englishName, Long year, String nation, String genre, String genreList, Long runTime, Double naverRating, Double reviewRating, String story, String image) {
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
}
