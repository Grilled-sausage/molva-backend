package com.grilledsausage.molva.config;

import com.grilledsausage.molva.api.entity.filmmaker.FilmmakerRepository;
import com.grilledsausage.molva.api.entity.movie.Movie;
import com.grilledsausage.molva.api.entity.movie.MovieRepository;
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


//            20228230,요정,Fairy,2022,한국,드라마,드라마,79
//            20224755,나는 마을 방과후 교사입니다,"The Teachers: pink, nature trail, ridge between rice paddies, plum",2022,한국,다큐멘터리,다큐멘터리,94
//            20228915,사랑하는 당신에게,Last Dance,2022,스위스,드라마,드라마,82
//            20196478,영웅,Hero,2022,한국,드라마,"드라마,뮤지컬",120
//            20229052,러브 플랫폼,The moment fall in love,2022,한국,드라마,드라마,
//                    19978205,암화,The Longest Nite,1997,홍콩,범죄,"범죄,스릴러",82
//            20228895,캐리와 슈퍼콜라,CARRIE&SUPERKOLA,2022,한국,애니메이션,애니메이션,79
//            20193581,감각의 제국 감독판,In the Realm of the Senses,1976,프랑스,드라마,"드라마,성인물(에로)",101
//            20228612,팬픽에서 연애까지,,2022,한국,멜로/로맨스,"멜로/로맨스,코미디",89
//            20204264,콘크리트 유토피아,,2021,한국,스릴러,스릴러,
//                    20226411,범죄도시3,THE ROUNDUP : NO WAY OUT,2022,한국,범죄,"범죄,액션",
//                    20229059,슬로우,Slow,2021,한국,드라마,드라마,3
//            20229049,"다시, 만남",MEET AGAIN,2022,한국,드라마,드라마,3
//            20229058,미스터 플라스틱의 하루,A Day of Mr. Plastic,2022,한국,애니메이션,애니메이션,2
//            20204548,범죄도시2,The Roundup,2022,한국,범죄,"범죄,액션",105
//            20229048,엄마의 산책,A comfortable moments,2022,한국,드라마,드라마,1

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
        }

    }

}
