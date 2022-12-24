package com.grilledsausage.molva.api.dto.reservation;

import com.grilledsausage.molva.api.entity.reservation.Reservation;
import lombok.Builder;
import lombok.Data;

@Data
public class MovieReservationResponseDto {

    private Long id;

    private Long code;

    private String name;

    private String image;

    @Builder
    public MovieReservationResponseDto(Long id, Long code, String name, String image) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.image = image;
    }

    public static MovieReservationResponseDto from(Reservation reservation) {
        return MovieReservationResponseDto
                .builder()
                .id(reservation.getMovie().getId())
                .code(reservation.getMovie().getCode())
                .name(reservation.getMovie().getName())
                .image(reservation.getMovie().getImage())
                .build();
    }

}
