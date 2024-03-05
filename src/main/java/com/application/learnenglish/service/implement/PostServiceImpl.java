package com.application.learnenglish.service.implement;

import com.application.learnenglish.exception.ApplicationRuntimeException;
import com.application.learnenglish.model.dto.PostCommentDTO;
import com.application.learnenglish.model.dto.PostRequest;
import com.application.learnenglish.model.dto.respone.PostRespone;
import com.application.learnenglish.model.entity.PostComment;
import com.application.learnenglish.model.entity.Posts;
import com.application.learnenglish.model.entity.User;
import com.application.learnenglish.model.enums.PostCategories;
import com.application.learnenglish.repository.PostCommentRepository;
import com.application.learnenglish.repository.PostRepository;
import com.application.learnenglish.repository.UserRepository;
import com.application.learnenglish.service.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepo;
    private final PostCommentRepository postCommentRepo;
    private final UserRepository userRepo;

    @Override
    public PostRequest addPost(PostRequest request) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findById(request.getUserId()).orElseThrow(() -> {
            throw new ApplicationRuntimeException("User is not exits!");
        });
        List<PostCategories> postCategories = List.of(PostCategories.IELTS, PostCategories.THPTQG, PostCategories.TOEIC);
        if (!postCategories.contains(request.getPostCategories())) {
            throw new ApplicationRuntimeException("Post category not exits!");
        }
        Posts posts = Posts.builder()
                .postContent(request.getPostContent())
                .postCategory(request.getPostCategories())
                .postDateCreated(LocalDateTime.now())
                .imagePostUrl(request.getImageUrlPost())
                .postLike(request.getPostLike())
                .createdBy(user)
                .build();
        postRepo.save(posts);
        return Posts.convertToPostDTO(posts);
    }

    @Override
    public List<PostRespone> getAllPost() {
        List<Posts> posts = postRepo.findAll();
        if (ObjectUtils.isEmpty(posts)) {
            return Collections.emptyList();
        }
        return posts.stream().sorted(Comparator.comparing(Posts::getPostDateCreated)).map(post -> PostRespone.builder()
                        .id(post.getId())
                        .postContent(post.getPostContent())
                        .imageUrlPost(post.getImagePostUrl())
                        .postCategories(post.getPostCategory())
                        .postLike(post.getPostLike())
                        .localDateTime(post.getPostDateCreated())
                        .createdBy(post.getCreatedBy())
                        .build())
                .collect(Collectors.toList());
    }

    private List<PostCommentDTO> mapResponse(Long id) {
        List<PostComment> postComments = postCommentRepo.findByPostId(id);
        if (ObjectUtils.isEmpty(postComments)) {
            return Collections.emptyList();
        }
        return postComments.stream()
                .sorted(Comparator.comparing(PostComment::getPcDateCreated))
                .map(postComment -> PostCommentDTO.builder()
                        .id(postComment.getId())
                        .content(postComment.getPcContent())
                        .urlImage(postComment.getUrlImage())
                        .user(postComment.getUser())
                        .localDateTime(postComment.getPcDateCreated())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public PostRespone getPostById(Long id) {
        Posts post = postRepo.findById(id).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Post not exits");
        });
        return PostRespone.builder()
                .id(post.getId())
                .postContent(post.getPostContent())
                .imageUrlPost(post.getImagePostUrl())
                .postLike(post.getPostLike())
                .postCategories(post.getPostCategory())
                .localDateTime(post.getPostDateCreated())
                .createdBy(post.getCreatedBy())
                .postCommentDTO(mapResponse(id))
                .build();
    }

    @Override
    public List<PostRespone> filterByPostCategory(String postCate) {
        if (!StringUtils.isEmpty(postCate) && Arrays.asList(PostCategories.values()).contains(PostCategories.valueOf(postCate))){
            List<Posts> posts = postRepo.filterByPostCate(PostCategories.valueOf(postCate));
            if (ObjectUtils.isEmpty(posts)) {
                return Collections.emptyList();
            }
            return posts.stream().sorted(Comparator.comparing(Posts::getPostDateCreated))
                    .map(post -> PostRespone.builder()
                            .id(post.getId())
                            .postContent(post.getPostContent())
                            .imageUrlPost(post.getImagePostUrl())
                            .postLike(post.getPostLike())
                            .localDateTime(post.getPostDateCreated())
                            .postCategories(post.getPostCategory())
                            .postCommentDTO(mapResponse(post.getId()))
                            .createdBy(post.getCreatedBy())
                            .build())
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void deletePostById(Long id) {
        Posts post = postRepo.findById(id).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Post not exits");
        });
        postRepo.delete(post);
    }

    @Override
    public Integer updateLike(Integer like, Long postId) {
        Posts post = postRepo.findById(postId).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Post is not exits!");
        });
        post.setPostLike(like);
        postRepo.save(post);
        Integer postLike = post.getPostLike();
        return postLike;
    }

}
