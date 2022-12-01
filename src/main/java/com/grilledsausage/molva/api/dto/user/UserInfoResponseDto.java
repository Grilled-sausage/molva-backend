package com.grilledsausage.molva.api.dto.user;

import com.grilledsausage.molva.api.entity.user.Role;
import com.grilledsausage.molva.api.entity.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponseDto {

    private Long id;

    private String email;

    private String nickname;

    private Role role;

    public static UserInfoResponseDto from(User user) {

        return UserInfoResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();

    }

}
