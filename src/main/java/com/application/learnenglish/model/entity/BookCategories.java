package com.application.learnenglish.model.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "book_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cate_book_id")
    private Long id;

    @Column(name = "cate_book_name")
    private String name;

    @Column(name = "cate_book_description")
    private String description;
    @Column(name = "cate_book_color")
    private String color;
    @OneToMany(mappedBy = "cateBookId", cascade = CascadeType.ALL)
    private List<Books> books;
}
