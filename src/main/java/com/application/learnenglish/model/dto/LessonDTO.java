package com.application.learnenglish.model.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    private Long id;
    private String title;
    private String content;
    private Long bookId;
    private List<VocabDTO> vocab;
}
