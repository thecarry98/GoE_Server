package com.application.learnenglish.model.entity;

import com.application.learnenglish.model.dto.PostRequest;
import com.application.learnenglish.model.enums.PostCategories;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    @Column(name = "post_content", columnDefinition = "text")
    private String postContent;
    @Column(name = "post_image")
    private String imagePostUrl;
    @Column(name = "post_date")
    private LocalDateTime postDateCreated;
    @Enumerated(EnumType.STRING)
    @Column(name = "post_categories")
    private PostCategories postCategory;
    @Column(name = "post_like")
    private Integer postLike;

    @JsonIgnore
    @CreatedBy
    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "created_by_user", referencedColumnName = "user_id", nullable = false, foreignKey = @ForeignKey(
            name = "fkey_post_created_by_user"
    ))
    private User createdBy;
    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<PostComment> postComments;

    public static PostRequest convertToPostDTO(Posts posts){
        return PostRequest.builder()
                .id(posts.getId())
                .postContent(posts.getPostContent())
                .imageUrlPost(posts.getImagePostUrl())
                .postCategories(posts.getPostCategory())
                .localDateTime(posts.getPostDateCreated())
                .postLike(posts.getPostLike())
                .build();
    }
}
