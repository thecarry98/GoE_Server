package com.application.learnenglish.model.entity;

import com.application.learnenglish.model.dto.SpeakingDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "speakings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Speakings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "speaking_id")
    private Long id;

    @Column(name = "speaking_name")
    private String speakingName;
    @JdbcTypeCode(SqlTypes.JSON)
    private Set<SpeakingItem> speakingItem = new HashSet<>();
    @JsonIgnore
    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    public static SpeakingDTO convertToSpeakingDTO(Speakings speaking){
        return SpeakingDTO.builder()
                .id(speaking.getId())
                .speakingName(speaking.getSpeakingName())
                .speakingItem(speaking.getSpeakingItem().stream()
                        .sorted(Comparator.comparing(SpeakingItem::getIndex))
                        .map(speakingItem1 -> SpeakingItem.builder()
                        .index(speakingItem1.getIndex())
                        .siAnswer(speakingItem1.getSiAnswer())
                        .siQuestion(speakingItem1.getSiQuestion())
                        .build()).collect(Collectors.toSet()))
                .build();
    };
}
