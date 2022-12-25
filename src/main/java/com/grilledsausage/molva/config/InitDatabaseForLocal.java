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
                    .code(20228230L)
                    .name("요정")
                    .englishName("Fairy")
                    .year(2022L)
                    .nation("한국")
                    .genre("드라마")
                    .genreList("드라마")
                    .runTime(79L)
                    .isInSurvey(true)
                    .build();

            movieRepository.save(movie1);

            Movie movie2 = Movie
                    .builder()
                    .code(20224755L)
                    .name("나는 마을 방과후 교사입니다")
                    .englishName("The Teachers: pink, nature trail, ridge between rice paddies, plum")
                    .year(2022L)
                    .nation("한국")
                    .genre("다큐멘터리")
                    .genreList("다큐멘터리")
                    .runTime(94L)
                    .isInSurvey(true)
                    .build();

            movieRepository.save(movie2);

            Movie movie3 = Movie
                    .builder()
                    .code(20228915L)
                    .name("사랑하는 당신에게")
                    .englishName("Last Dance")
                    .year(2022L)
                    .nation("스위스")
                    .genre("드라마")
                    .genreList("드라마")
                    .runTime(82L)
                    .isInSurvey(false)
                    .build();

            movieRepository.save(movie3);

            Filmmaker filmmaker1 = Filmmaker
                    .builder()
                    .code(1L)
                    .name("김민근")
                    .type("배우")
                    .isInSurvey(true)
                    .build();

            filmmakerRepository.save(filmmaker1);

            Filmmaker filmmaker2 = Filmmaker
                    .builder()
                    .code(2L)
                    .name("윤석민")
                    .type("감독")
                    .isInSurvey(true)
                    .build();

            filmmakerRepository.save(filmmaker2);

            Filmmaker filmmaker3 = Filmmaker
                    .builder()
                    .code(3L)
                    .name("박동진")
                    .type("배우")
                    .isInSurvey(false)
                    .build();

            filmmakerRepository.save(filmmaker3);

            Filmmaker filmmaker4 = Filmmaker
                    .builder()
                    .code(4L)
                    .name("송현섭")
                    .type("배우")
                    .isInSurvey(false)
                    .build();

            filmmakerRepository.save(filmmaker4);

            Filmmaker filmmaker5 = Filmmaker
                    .builder()
                    .code(5L)
                    .name("임영준")
                    .type("감독")
                    .isInSurvey(false)
                    .build();

            filmmakerRepository.save(filmmaker5);

            Filmmaker filmmaker6 = Filmmaker
                    .builder()
                    .code(6L)
                    .name("김수동")
                    .type("감독")
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
