package com.application.learnenglish.model.dto.respone;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookHomePageResponse {
    private Long id;
    private String author;
    private String image;
    private String title;
    private String released;
    private List<LessonHomePage> lessons;
}
