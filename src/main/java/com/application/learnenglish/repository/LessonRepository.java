package com.application.learnenglish.repository;

import com.application.learnenglish.model.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query("SELECT l FROM Lesson l WHERE l.books.id=:bookId")
    List<Lesson> findByBookId(@Param("bookId") Long bookId);
}
