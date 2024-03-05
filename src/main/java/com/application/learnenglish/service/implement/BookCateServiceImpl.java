package com.application.learnenglish.service.implement;

import com.application.learnenglish.exception.ApplicationRuntimeException;
import com.application.learnenglish.model.dto.BookCateDTO;
import com.application.learnenglish.model.entity.BookCategories;
import com.application.learnenglish.repository.BookCateRepository;
import com.application.learnenglish.service.BookCateService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCateServiceImpl implements BookCateService {
    private final BookCateRepository bookCateRepo;

    @Override
    public void addBookCate(BookCateDTO request) {
        if (ObjectUtils.isNotEmpty(bookCateRepo.findByName(request.getBookCateName()))){
            throw new ApplicationRuntimeException("Book cate already!");
        }
        BookCategories bookCategories = BookCategories.builder()
                .id(request.getId())
                .name(request.getBookCateName())
                .description(request.getDescription())
                .color(request.getColor())
                .build();
        bookCateRepo.save(bookCategories);
    }

    @Override
    public List<BookCateDTO> getAllBookCate() {
        List<BookCategories> bookCategories = bookCateRepo.findAll();
        List<BookCateDTO> bookCateDTOS = new ArrayList<>();
        for (BookCategories bookCate : bookCategories) {
            BookCateDTO bookCateDto = BookCateDTO.builder()
                    .id(bookCate.getId())
                    .bookCateName(bookCate.getName())
                    .description(bookCate.getDescription())
                    .color(bookCate.getColor())
                    .build();
            bookCateDTOS.add(bookCateDto);
        }
        return bookCateDTOS;
    }

    @Override
    public void deleteBookCate(Long id) {
        BookCategories bookCate = bookCateRepo.findById(id).orElseThrow(() -> new ApplicationRuntimeException("Book Category not exits!"));
        if (ObjectUtils.isNotEmpty(bookCate)){
            bookCateRepo.delete(bookCate);
        }
    }

    @Override
    public BookCateDTO updateCateBook(BookCateDTO request) {
        BookCategories bookCate = bookCateRepo.findById(request.getId()).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Book Category not exits!");
        });
        bookCate.setColor(request.getColor());
        bookCate.setDescription(request.getDescription());
        bookCate.setName(request.getBookCateName());
        bookCateRepo.save(bookCate);
        return BookCateDTO.builder()
                .id(bookCate.getId())
                .bookCateName(bookCate.getName())
                .color(bookCate.getColor())
                .description(bookCate.getDescription())
                .build();
    }

}
