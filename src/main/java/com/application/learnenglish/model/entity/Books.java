package com.application.learnenglish.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.annotation.CreatedBy;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_description")
    private String bookDescription;
    @Column(name = "book_image")
    private String imageUrl;
    @JsonIgnore
    @ManyToOne(targetEntity = BookCategories.class, optional = false)
    @JoinColumn(name = "cate_book_id", referencedColumnName = "cate_book_id", nullable = false)
    private BookCategories cateBookId;
    @JsonIgnore
    @CreatedBy
    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "created_by_user", referencedColumnName = "user_id", nullable = false, foreignKey = @ForeignKey(
            name = "fkey_book_created_by_user"
    ))
    private User createdBy;
    @OneToMany(mappedBy = "books", cascade = CascadeType.ALL)
    private List<Lesson> lessons;

}
