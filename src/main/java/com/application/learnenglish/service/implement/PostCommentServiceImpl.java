package com.application.learnenglish.service.implement;

import com.application.learnenglish.exception.ApplicationRuntimeException;
import com.application.learnenglish.model.dto.PostCommentDTO;
import com.application.learnenglish.model.entity.PostComment;
import com.application.learnenglish.model.entity.Posts;
import com.application.learnenglish.model.entity.User;
import com.application.learnenglish.repository.PostCommentRepository;
import com.application.learnenglish.repository.PostRepository;
import com.application.learnenglish.repository.UserRepository;
import com.application.learnenglish.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {
    private final PostCommentRepository postCommentRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    @Override
    public PostCommentDTO addComment(PostCommentDTO request, Long postId) {
        Posts post = postRepo.findById(postId).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Post is not exits!");
        });
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findById(request.getUser().getId()).orElseThrow(() -> {
            throw new ApplicationRuntimeException("User is not exits");
        });
        PostComment postComment = PostComment.builder()
                .pcContent(request.getContent())
                .urlImage(request.getUrlImage())
                .pcDateCreated(LocalDateTime.now())
                .posts(post)
                .user(user)
                .build();
        postCommentRepo.save(postComment);
        return PostComment.convertToResponse(postComment);
    }

    @Override

    public PostCommentDTO editComment(PostCommentDTO request) {
        PostComment postComment = postCommentRepo.findById(request.getId()).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Post is not exits!");
        });
        postComment.setPcContent(request.getContent());
        postComment.setUrlImage(request.getUrlImage());
        postCommentRepo.save(postComment);
        return PostComment.convertToResponse(postComment);
    }

    @Override
    public void deleteComment(Long id) {
        PostComment postComment = postCommentRepo.findById(id).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Post is not exits!");
        });
        postCommentRepo.delete(postComment);
    }
}
