package com.application.learnenglish.repository;

import com.application.learnenglish.model.entity.PostComment;
import com.application.learnenglish.model.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    @Query("SELECT pc FROM PostComment pc WHERE pc.posts.id=:postId")
    List<PostComment> findByPostId(@Param("postId") Long postId);
}
