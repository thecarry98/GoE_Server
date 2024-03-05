package com.application.learnenglish.model.dto;

import com.application.learnenglish.model.enums.PostCategories;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long id;
    private String postContent;
    private String imageUrlPost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;
    private Integer postLike;
    private PostCategories postCategories;
    private Long userId;
}
