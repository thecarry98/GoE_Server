package com.application.learnenglish.model.dto.respone;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Long id;
    private String bookName;
    private String bookDescription;
    private String urlImage;
    private String cateBook;
    private List<LessonResponse> lessons;
}
