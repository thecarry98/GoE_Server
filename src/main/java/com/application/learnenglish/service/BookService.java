package com.application.learnenglish.service;

import com.application.learnenglish.model.dto.BookDTO;
import com.application.learnenglish.model.dto.respone.BookHomePageResponse;
import com.application.learnenglish.model.dto.respone.BookResponse;

import java.util.List;

public interface BookService {
    void addBook(BookDTO request);
    void updateBook(BookDTO request);
    List<BookDTO> getAllBook();
    List<BookDTO> getAllBookByCateBook(Long cateBookId);
    List<BookHomePageResponse> getHomePage();
    BookResponse getBookById(Long bookId);
    void deleteBook(Long bookId);
}
