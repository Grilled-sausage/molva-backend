package com.grilledsausage.molva.config;

import com.grilledsausage.molva.api.entity.user.User;
import com.grilledsausage.molva.api.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Profile("local")
@Component
public class InitDatabaseForLocal {

    private final InitDatabaseForLocalService initDatabaseForLocalService;

    @PostConstruct
    private void init() {
        this.initDatabaseForLocalService.init();
    }

    @RequiredArgsConstructor
    @Component
    private static class InitDatabaseForLocalService {

        private final UserRepository userRepository;

        @Transactional
        public void init() {

            User user1 = User
                    .oAuth2Register()
                    .email("mkkim999@gmail.com")
                    .password("88888888")
                    .build();

            user1.setNickname("mingeuni__");
            userRepository.save(user1);

            User user2 = User
                    .oAuth2Register()
                    .email("tlsghk625@gmail.com")
                    .password("88888888")
                    .build();

            user2.setNickname("say_hi");
            userRepository.save(user2);

        }

    }

}
