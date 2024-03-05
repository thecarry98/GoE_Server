package com.application.learnenglish.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "my_vocab")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyVocab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_vocab_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
    @JsonIgnore
    @ManyToOne(targetEntity = Vocab.class, optional = false)
    @JoinColumn(name = "vocab_id", referencedColumnName = "vocab_id", nullable = false)
    private Vocab vocab;


}
