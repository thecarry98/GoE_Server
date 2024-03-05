package com.application.learnenglish.model.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "cate_vocab")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CateVocabs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cate_vocab_id")
    private Long id;

    @Column(name = "cate_vocab_name")
    private String cateVocabName;
}
