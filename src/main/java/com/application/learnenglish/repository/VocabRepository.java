package com.application.learnenglish.repository;

import com.application.learnenglish.model.entity.Vocab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabRepository extends JpaRepository<Vocab, Long> {
    @Query("select v from Vocab v where v.lessons.id=:lessonId")
    List<Vocab> findAllByLessonId(@Param("lessonId") Long lessonId);
}
