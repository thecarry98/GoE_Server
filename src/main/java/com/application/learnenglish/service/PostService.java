package com.application.learnenglish.service;

import com.application.learnenglish.model.dto.PostRequest;
import com.application.learnenglish.model.dto.respone.PostRespone;

import java.util.List;

public interface PostService {
    PostRequest addPost(PostRequest request);
    List<PostRespone> getAllPost();
    PostRespone getPostById(Long id);
    List<PostRespone> filterByPostCategory(String postCate);
    void deletePostById(Long id);
    Integer updateLike(Integer like, Long postId);
}
