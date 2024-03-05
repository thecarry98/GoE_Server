package com.application.learnenglish.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpeakingItem {
    @NotBlank
    private String siQuestion;
    @NotBlank
    private String siAnswer;
    private Integer index;
}
