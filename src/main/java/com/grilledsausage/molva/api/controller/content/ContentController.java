package com.grilledsausage.molva.api.controller.content;

import com.grilledsausage.molva.api.dto.content.SurveyMovieResponseDto;
import com.grilledsausage.molva.api.service.content.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ContentService contentService;

    @GetMapping("/survey/movie")
    public List<SurveyMovieResponseDto> getSurveyMovieList() {

        return contentService.getSurveyMovieList();
    }

}
