package com.grilledsausage.molva.api.entity.participation;

import com.grilledsausage.molva.api.entity.filmmaker.Filmmaker;
import com.grilledsausage.molva.api.entity.movie.Movie;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Participation")
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "filmmaker_id", nullable = false)
    private Filmmaker filmmaker;

}
