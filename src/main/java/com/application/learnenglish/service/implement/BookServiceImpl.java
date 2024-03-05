package com.application.learnenglish.service.implement;

import com.application.learnenglish.exception.ApplicationRuntimeException;
import com.application.learnenglish.model.dto.BookDTO;
import com.application.learnenglish.model.dto.LessonDTO;
import com.application.learnenglish.model.dto.VocabDTO;
import com.application.learnenglish.model.dto.respone.BookHomePageResponse;
import com.application.learnenglish.model.dto.respone.BookResponse;
import com.application.learnenglish.model.dto.respone.LessonHomePage;
import com.application.learnenglish.model.dto.respone.LessonResponse;
import com.application.learnenglish.model.entity.*;
import com.application.learnenglish.repository.BookCateRepository;
import com.application.learnenglish.repository.BookRepository;
import com.application.learnenglish.repository.LessonRepository;
import com.application.learnenglish.repository.VocabRepository;
import com.application.learnenglish.service.BookService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepo;
    private final BookCateRepository bookCateRepo;
    private final LessonRepository lessonRepo;
    private final VocabRepository vocabRepo;

    @Override
    public void addBook(BookDTO request) {
        Books book = bookRepo.findByBookName(request.getBookName());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ObjectUtils.isNotEmpty(book)) {
            throw new ApplicationRuntimeException("Book is already!");
        }
        BookCategories bookCategories = bookCateRepo.findByName(request.getCateBook());
        if (ObjectUtils.isEmpty(bookCategories)) {
            throw new ApplicationRuntimeException("Book category not exits!");
        }
        bookRepo.save(Books.builder()
                .bookName(request.getBookName())
                .bookDescription(request.getBookDescription())
                .imageUrl(request.getUrlImage())
                .createdBy(user)
                .cateBookId(bookCategories)
                .build());
    }

    @Override
    public void updateBook(BookDTO request) {
        Books book = bookRepo.findById(request.getId()).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Book is not exits!");
        });
        book.setBookName(request.getBookName());
        book.setBookDescription(request.getBookDescription());
        book.setImageUrl(request.getUrlImage());
        bookRepo.save(book);
    }

    @Override
    public List<BookDTO> getAllBook() {
        List<Books> books = bookRepo.findAll();
        if (ObjectUtils.isEmpty(books)) {
            return Collections.emptyList();
        }
        return books.stream().sorted(Comparator.comparing(Books::getId)).map(book -> BookDTO.builder()
                .id(book.getId())
                .cateBook(book.getCateBookId().getName())
                .bookName(book.getBookName())
                .bookDescription(book.getBookDescription())
                .urlImage(book.getImageUrl())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getAllBookByCateBook(Long id) {
        List<Books> books = bookRepo.findAllByCateBookId(id);
        if (ObjectUtils.isEmpty(books)) {
            return Collections.emptyList();
        }
        return books.stream()
                .sorted(Comparator.comparing(Books::getId))
                .map(book -> BookDTO.builder()
                        .id(book.getId())
                        .bookName(book.getBookName())
                        .bookDescription(book.getBookDescription())
                        .urlImage(book.getImageUrl())
                        .cateBook(book.getCateBookId().getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<BookHomePageResponse> getHomePage() {
        List<Books> books = bookRepo.findAll();
        if (ObjectUtils.isEmpty(books)) {
            return Collections.emptyList();
        }
        return books.stream()
                .sorted(Comparator.comparing(Books::getId))
                .map(book -> BookHomePageResponse.builder()
                        .id(book.getId())
                        .author("HOANG TU")
                        .released("2023")
                        .image(book.getImageUrl())
                        .title(book.getBookName())
                        .lessons(getLessonByBookId(book.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse getBookById(Long bookId) {
        Books book = bookRepo.findById(bookId).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Book is not exits!");
        });
        return BookResponse.builder()
                .id(book.getId())
                .cateBook(book.getCateBookId().getName())
                .bookName(book.getBookName())
                .bookDescription(book.getBookDescription())
                .urlImage(book.getImageUrl())
                .lessons(this.getLessonByBook(book.getId()))
                .build();
    }

    @Override
    public void deleteBook(Long bookId) {
        Books book = bookRepo.findById(bookId).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Book is not exits!");
        });
        bookRepo.delete(book);
    }

    private List<LessonResponse> getLessonByBook(Long id) {
        List<Lesson> lessons = lessonRepo.findByBookId(id);
        if (ObjectUtils.isEmpty(lessons)) {
            return Collections.emptyList();
        }
        return lessons.stream().sorted(Comparator.comparing(Lesson::getId)).map(lesson -> LessonResponse.builder()
                        .id(lesson.getId())
                        .title(lesson.getTitle())
                        .content(lesson.getContent())
                        .build())
                .collect(Collectors.toList());

    }

    private List<LessonHomePage> getLessonByBookId(Long id) {
        List<Lesson> lessons = lessonRepo.findByBookId(id);
        if (ObjectUtils.isEmpty(lessons)) {
            return Collections.emptyList();
        }
        return lessons.stream()
                .sorted(Comparator.comparing(Lesson::getId))
                .map(lesson -> LessonHomePage.builder()
                        .lessonId(lesson.getId())
                        .title(lesson.getTitle())
                        .content(lesson.getContent())
                        .vocabs(getVocabByLessonId(lesson.getId()))
                        .build())
                .collect(Collectors.toList());

    }

    private List<VocabDTO> getVocabByLessonId(Long id) {
        List<Vocab> vocabs = vocabRepo.findAllByLessonId(id);
        if (ObjectUtils.isEmpty(vocabs)) {
            return Collections.emptyList();
        }
        return vocabs.stream()
                .sorted(Comparator.comparing(Vocab::getId))
                .map(vocab -> VocabDTO.builder()
                        .id(vocab.getId())
                        .cateVocab(vocab.getCateVocab().getCateVocabName())
                        .enVocab(vocab.getEnVocab())
                        .vnVocab(vocab.getVnVocab())
                        .index(vocab.getIndex())
                        .build())
                .collect(Collectors.toList());

    }
}
