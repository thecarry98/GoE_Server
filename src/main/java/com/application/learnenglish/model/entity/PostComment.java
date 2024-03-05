package com.application.learnenglish.model.entity;

import com.application.learnenglish.model.dto.PostCommentDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pc_id")
    private Long id;

    @Column(name = "pc_content", columnDefinition = "text")
    private String pcContent;
    @Column(name = "pc_image")
    private String urlImage;
    @Column(name = "post_comment_date")
    private LocalDateTime pcDateCreated;
    @JsonIgnore
    @ManyToOne(targetEntity = Posts.class, optional = false)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id", nullable = false)
    private Posts posts;

    @JsonIgnore
    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    public static PostCommentDTO convertToResponse(PostComment postComment){
        return PostCommentDTO.builder()
                .id(postComment.getId())
                .content(postComment.getPcContent())
                .urlImage(postComment.getUrlImage())
                .localDateTime(postComment.getPcDateCreated())
                .user(postComment.getUser())
                .build();
    }
}
