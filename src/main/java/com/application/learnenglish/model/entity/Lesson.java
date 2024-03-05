package com.application.learnenglish.model.entity;

import com.application.learnenglish.model.dto.LessonDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "lesson_id")
    private Long id;

    @Column(name = "lesson_title")
    @NotBlank
    private String title;

    @Column(name ="lesson_content", columnDefinition = "text")
    @NotBlank
    private String content;

    @ManyToOne(targetEntity = Books.class, optional = false)
    @JoinColumn(name = "book_id", referencedColumnName = "book_id", nullable = false)
    @JsonIgnore
    private Books books;
    @OneToMany(mappedBy = "lessons", cascade = CascadeType.ALL)
    private List<Vocab> vocabs;
}
