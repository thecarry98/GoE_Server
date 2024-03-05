package com.application.learnenglish.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vocab")
public class Vocab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vocab_id")
    private Long id;

    @Column(name = "eng_vocab")
    private String enVocab;

    @Column(name = "vn_vocab")
    private String vnVocab;
    private String index;

    @JsonIgnore
    @ManyToOne(targetEntity = Lesson.class, optional = false)
    @JoinColumn(name = "lesson_id", referencedColumnName = "lesson_id", nullable = false)
    private Lesson lessons;

    @JsonIgnore
    @ManyToOne(targetEntity = CateVocabs.class, optional = false)
    @JoinColumn(name = "cate_vocab_id", referencedColumnName = "cate_vocab_id", nullable = false)
    private CateVocabs cateVocab;
}
