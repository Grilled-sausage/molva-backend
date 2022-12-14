package com.grilledsausage.molva.config;

import com.grilledsausage.molva.api.entity.filmmaker.Filmmaker;
import com.grilledsausage.molva.api.entity.filmmaker.FilmmakerRepository;
import com.grilledsausage.molva.api.entity.movie.Movie;
import com.grilledsausage.molva.api.entity.movie.MovieRepository;
import com.grilledsausage.molva.api.entity.participation.Participation;
import com.grilledsausage.molva.api.entity.participation.ParticipationRepository;
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

        private final MovieRepository movieRepository;

        private final FilmmakerRepository filmmakerRepository;

        private final ParticipationRepository participationRepository;

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

            Movie movie1 = Movie
                    .builder()
                    .code(19900248L)
                    .name("??? ??? ??? ??????")
                    .englishName("Fairy")
                    .year(1990L)
                    .nation("??????")
                    .genre("SF")
                    .genreList("SF,?????????,????????????")
                    .runTime(117L)
                    .isInSurvey(true)
                    .build();

            movieRepository.save(movie1);

            Movie movie2 = Movie
                    .builder()
                    .code(19900204L)
                    .name("?????? ????????? ??????")
                    .englishName("The Teachers: pink, nature trail, ridge between rice paddies, plum")
                    .year(1989L)
                    .nation("??????")
                    .genre("?????????")
                    .genreList("?????????")
                    .runTime(94L)
                    .isInSurvey(true)
                    .build();

            movieRepository.save(movie2);

            Movie movie3 = Movie
                    .builder()
                    .code(20228915L)
                    .name("???????????? ????????????")
                    .englishName("Last Dance")
                    .year(2022L)
                    .nation("?????????")
                    .genre("?????????")
                    .genreList("?????????")
                    .runTime(82L)
                    .isInSurvey(false)
                    .build();

            movieRepository.save(movie3);

            Filmmaker filmmaker1 = Filmmaker
                    .builder()
                    .code(1L)
                    .name("?????????")
                    .type("??????")
                    .isInSurvey(true)
                    .build();

            filmmakerRepository.save(filmmaker1);

            Filmmaker filmmaker2 = Filmmaker
                    .builder()
                    .code(2L)
                    .name("?????????")
                    .type("??????")
                    .isInSurvey(true)
                    .build();

            filmmakerRepository.save(filmmaker2);

            Filmmaker filmmaker3 = Filmmaker
                    .builder()
                    .code(3L)
                    .name("?????????")
                    .type("??????")
                    .isInSurvey(false)
                    .build();

            filmmakerRepository.save(filmmaker3);

            Filmmaker filmmaker4 = Filmmaker
                    .builder()
                    .code(4L)
                    .name("?????????")
                    .type("??????")
                    .isInSurvey(false)
                    .build();

            filmmakerRepository.save(filmmaker4);

            Filmmaker filmmaker5 = Filmmaker
                    .builder()
                    .code(5L)
                    .name("?????????")
                    .type("??????")
                    .isInSurvey(false)
                    .build();

            filmmakerRepository.save(filmmaker5);

            Filmmaker filmmaker6 = Filmmaker
                    .builder()
                    .code(6L)
                    .name("?????????")
                    .type("??????")
                    .isInSurvey(false)
                    .build();

            filmmakerRepository.save(filmmaker6);

            Participation participation1 = Participation
                    .builder()
                    .movie(movie1)
                    .filmmaker(filmmaker1)
                    .build();


            participationRepository.save(participation1);

            Participation participation2 = Participation
                    .builder()
                    .movie(movie1)
                    .filmmaker(filmmaker2)
                    .build();


            participationRepository.save(participation2);

            Participation participation3 = Participation
                    .builder()
                    .movie(movie2)
                    .filmmaker(filmmaker3)
                    .build();


            participationRepository.save(participation3);

            Participation participation4 = Participation
                    .builder()
                    .movie(movie1)
                    .filmmaker(filmmaker4)
                    .build();


            participationRepository.save(participation4);

            Participation participation5 = Participation
                    .builder()
                    .movie(movie1)
                    .filmmaker(filmmaker5)
                    .build();


            participationRepository.save(participation5);

            Participation participation6 = Participation
                    .builder()
                    .movie(movie1)
                    .filmmaker(filmmaker6)
                    .build();


            participationRepository.save(participation6);

            Participation participation7 = Participation
                    .builder()
                    .movie(movie2)
                    .filmmaker(filmmaker6)
                    .build();


            participationRepository.save(participation7);
        }

    }

}
