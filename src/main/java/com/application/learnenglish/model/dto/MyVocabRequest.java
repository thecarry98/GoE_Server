package com.application.learnenglish.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyVocabRequest {
    private Long userId;
    private Long vocabId;
}
