package com.grilledsausage.molva.api.entity.rating;

import com.grilledsausage.molva.api.entity.movie.Movie;
import com.grilledsausage.molva.api.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "user_rating", nullable = false)
    private Double userRating;

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    @Builder
    public Rating(User user, Movie movie, Double userRating) {
        this.user = user;
        this.movie = movie;
        this.userRating = userRating;
    }
}
