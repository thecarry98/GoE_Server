package com.application.learnenglish.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VocabDTO {
    private Long id;
    private String vnVocab;
    private String enVocab;
    private String index;
    private String cateVocab;
}
