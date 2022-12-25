package com.grilledsausage.molva.api.dto.preference;

import com.grilledsausage.molva.api.entity.filmmaker.Filmmaker;
import lombok.Builder;
import lombok.Data;

@Data
public class GetPreferredFilmmakersResponseDto {

    private Long id;

    private String name;

    private String type;

    private String image;

    @Builder
    public GetPreferredFilmmakersResponseDto(Long id, String name, String type, String image) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.image = image;
    }

    public static GetPreferredFilmmakersResponseDto from(Filmmaker filmmaker) {
        return GetPreferredFilmmakersResponseDto
                .builder()
                .id(filmmaker.getId())
                .name(filmmaker.getName())
                .type(filmmaker.getType())
                .image(filmmaker.getImage())
                .build();
    }

}
