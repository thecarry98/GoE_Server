package com.application.learnenglish.model.dto.respone;

import com.application.learnenglish.model.dto.VocabDTO;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonHomePage {
    private Long lessonId;
    private String title;
    private String content;
    private List<VocabDTO> vocabs;
}
