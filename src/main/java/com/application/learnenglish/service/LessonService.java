package com.application.learnenglish.service;

import com.application.learnenglish.model.dto.LessonDTO;
import com.application.learnenglish.model.dto.respone.LessonVocabResponse;

public interface LessonService {
    void addLesson(LessonDTO request);
    void updateLesson(LessonDTO request);
    LessonVocabResponse getLessonById(Long id);
    void deleteLesson(Long lessonId);
}
