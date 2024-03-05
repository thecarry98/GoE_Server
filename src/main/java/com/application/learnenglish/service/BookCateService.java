package com.application.learnenglish.service;

import com.application.learnenglish.model.dto.BookCateDTO;

import java.util.List;

public interface BookCateService {
    void addBookCate(BookCateDTO request);
    List<BookCateDTO> getAllBookCate();
    void deleteBookCate(Long id);
    BookCateDTO updateCateBook(BookCateDTO bookCate);

}
