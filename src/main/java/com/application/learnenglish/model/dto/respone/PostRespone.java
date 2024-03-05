package com.application.learnenglish.model.dto.respone;

import com.application.learnenglish.model.dto.PostCommentDTO;
import com.application.learnenglish.model.entity.User;
import com.application.learnenglish.model.enums.PostCategories;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRespone {
    private Long id;
    private String postContent;
    private String imageUrlPost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;
    private Integer postLike;
    private PostCategories postCategories;
    private List<PostCommentDTO> postCommentDTO;
    private User createdBy;
}
