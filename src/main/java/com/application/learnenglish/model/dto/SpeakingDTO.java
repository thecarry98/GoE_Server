package com.application.learnenglish.model.dto;

import com.application.learnenglish.model.entity.SpeakingItem;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpeakingDTO {
    private Long id;
    private String speakingName;
    private Set<SpeakingItem> speakingItem = new HashSet<>();
}
