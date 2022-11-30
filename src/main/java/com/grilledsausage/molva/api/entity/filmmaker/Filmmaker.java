package com.grilledsausage.molva.api.entity.filmmaker;

import com.grilledsausage.molva.api.entity.participation.Participation;
import lombok.AccessLevel;
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
    @Column(name = "filmmaker_id", nullable = false)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private Long code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "filmmaker", fetch = FetchType.LAZY)
    private List<Participation> participations = new ArrayList<>();

}
