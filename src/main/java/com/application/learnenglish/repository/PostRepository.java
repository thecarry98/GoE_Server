package com.application.learnenglish.repository;

import com.application.learnenglish.model.entity.Posts;
import com.application.learnenglish.model.enums.PostCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {
    @Query("SELECT p FROM Posts p WHERE p.postCategory=:postCate")
    List<Posts> filterByPostCate(@Param("postCate") PostCategories postCate);
}
