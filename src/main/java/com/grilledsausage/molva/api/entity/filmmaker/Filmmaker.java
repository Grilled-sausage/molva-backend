package com.grilledsausage.molva.api.entity.filmmaker;

import com.grilledsausage.molva.api.entity.participation.Participation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Filmmaker")
public class Filmmaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "filmmaker_id")
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private Long code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "is_in_survey", nullable = false)
    private Boolean isInSurvey;

    @OneToMany(mappedBy = "filmmaker", fetch = FetchType.LAZY)
    private List<Participation> participations = new ArrayList<>();

    @Builder
    public Filmmaker(Long code, String name, String type, String image, Boolean isInSurvey) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.image = image;
        this.isInSurvey = isInSurvey;
    }

}
