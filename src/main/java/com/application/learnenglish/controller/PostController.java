package com.application.learnenglish.controller;

import com.application.learnenglish.model.dto.PostCommentDTO;
import com.application.learnenglish.model.dto.PostRequest;
import com.application.learnenglish.service.PostCommentService;
import com.application.learnenglish.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "Post Controller")
public class PostController {
    private final PostService postService;
    private final PostCommentService postCommentService;
    @PostMapping("/post")
    @Operation(summary = "add post")
    public ResponseEntity<?> addPost(@RequestBody PostRequest request) {
        return new ResponseEntity<>(postService.addPost(request), HttpStatus.OK);
    }
    @GetMapping("/post")
    public ResponseEntity<?> getPost() {
        return new ResponseEntity<>(postService.getAllPost(), HttpStatus.OK);
    }
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable(name = "postId") Long postId) {
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }
    @GetMapping("/post-cate")
    public ResponseEntity<?> getPostWithCate(@RequestParam(name = "postCate") String postCategory) {
        return new ResponseEntity<>(postService.filterByPostCategory(postCategory), HttpStatus.OK);
    }
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "postId") Long postId) {
        postService.deletePostById(postId);
        return new ResponseEntity<>("Delete post successfully!", HttpStatus.OK);
    }

    @PostMapping("/post/post-comment")
    public ResponseEntity<?> addComment(@RequestBody PostCommentDTO request,
                                        @RequestParam(name = "postId") Long postId) {
        return new ResponseEntity<>(postCommentService.addComment(request, postId), HttpStatus.OK);
    }
    @PutMapping("/post/post-comment")
    public ResponseEntity<?> editComment(@RequestBody PostCommentDTO request) {
        return new ResponseEntity<>(postCommentService.editComment(request), HttpStatus.OK);
    }
    @DeleteMapping("/post/post-comment")
    public ResponseEntity<?> deleteComment(@RequestParam(name = "postCommentId") Long postCommentId) {
        postCommentService.deleteComment(postCommentId);
        return new ResponseEntity<>("Delete comment successfully!", HttpStatus.OK);
    }
    @PutMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@RequestParam(name = "like") Integer like,
                                      @PathVariable(name = "postId") Long postId) {
        return new ResponseEntity<>(postService.updateLike(like, postId), HttpStatus.OK);
    }

}
