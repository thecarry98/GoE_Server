package com.application.learnenglish.repository;

import com.application.learnenglish.model.entity.MyVocab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyVocabRepository extends JpaRepository<MyVocab, Long> {
    @Query("select mv from MyVocab mv where mv.user.id=:userId")
    List<MyVocab> findByUserId(@Param("userId") Long userId);
}
