package com.application.learnenglish.service;

import com.application.learnenglish.model.dto.PostCommentDTO;

public interface PostCommentService {
    PostCommentDTO addComment(PostCommentDTO request, Long postId);
    PostCommentDTO editComment(PostCommentDTO request);
    void deleteComment(Long id);
}
