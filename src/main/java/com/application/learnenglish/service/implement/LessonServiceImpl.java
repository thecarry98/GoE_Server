package com.application.learnenglish.service.implement;

import com.application.learnenglish.exception.ApplicationRuntimeException;
import com.application.learnenglish.model.dto.LessonDTO;
import com.application.learnenglish.model.dto.VocabDTO;
import com.application.learnenglish.model.dto.respone.LessonVocabResponse;
import com.application.learnenglish.model.entity.Books;
import com.application.learnenglish.model.entity.CateVocabs;
import com.application.learnenglish.model.entity.Lesson;
import com.application.learnenglish.model.entity.Vocab;
import com.application.learnenglish.repository.BookRepository;
import com.application.learnenglish.repository.CateVocabRepository;
import com.application.learnenglish.repository.LessonRepository;
import com.application.learnenglish.repository.VocabRepository;
import com.application.learnenglish.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepo;
    private final BookRepository bookRepo;
    private final VocabRepository vocabRepo;
    private final CateVocabRepository cateVocabRepo;

    @Override
    public void addLesson(LessonDTO request) {
        Books book = bookRepo.findById(request.getBookId()).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Book is not exits!");
        });
        Lesson lesson = Lesson.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .books(book)
                .build();
        lessonRepo.save(lesson);
        for (VocabDTO vocab : request.getVocab()) {
            this.addVocab(vocab, lesson);
        }
    }

    private void addVocab(VocabDTO request, Lesson lessonId) {
        CateVocabs cateVocab = cateVocabRepo.findByCateVocabName(request.getCateVocab());
        if (ObjectUtils.isEmpty(cateVocab)) {
            throw new ApplicationRuntimeException("Vocab category is not exits!");
        }
        Vocab vocab = Vocab.builder()
                .enVocab(request.getEnVocab())
                .vnVocab(request.getVnVocab())
                .cateVocab(cateVocab)
                .lessons(lessonId)
                .index(request.getIndex())
                .build();
        vocabRepo.save(vocab);
    }

    @Override
    public void updateLesson(LessonDTO request) {
        Lesson lesson = lessonRepo.findById(request.getId()).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Lesson is not exits!");
        });
        lesson.setTitle(request.getTitle());
        lesson.setContent(lesson.getContent());
        lessonRepo.save(lesson);
    }

    @Override
    public LessonVocabResponse getLessonById(Long id) {
        Lesson lesson = lessonRepo.findById(id).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Lesson is not exits!");
        });
        return LessonVocabResponse.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .vocab(this.getVocabByLesson(lesson.getId()))
                .build();
    }

    @Override
    public void deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Lesson is not exits!");
        });
        lessonRepo.delete(lesson);
    }

    private List<VocabDTO> getVocabByLesson(Long lessonId) {
        List<Vocab> vocabs = vocabRepo.findAllByLessonId(lessonId);
        if (ObjectUtils.isEmpty(vocabs)) {
            return Collections.emptyList();
        }
        return vocabs.stream()
                .sorted(Comparator.comparing(Vocab::getId))
                .map(vocab -> VocabDTO.builder()
                        .id(vocab.getId())
                        .vnVocab(vocab.getVnVocab())
                        .enVocab(vocab.getEnVocab())
                        .index(vocab.getIndex())
                        .cateVocab(vocab.getCateVocab().getCateVocabName())
                        .build())
                .collect(Collectors.toList());
    }
}
