package com.application.learnenglish.controller;

import com.application.learnenglish.model.dto.BookCateDTO;
import com.application.learnenglish.model.dto.BookDTO;
import com.application.learnenglish.service.BookCateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "BookCate Controller", description = "Controller for BookCate")
public class BookCateController {
    private final BookCateService bookCateService;

    @PostMapping("/admin/book-categories")
    @Operation(summary = "add BookCate by use")
    public ResponseEntity<?> addBookCate(@RequestBody BookCateDTO request) {
        bookCateService.addBookCate(request);
        return new ResponseEntity<>("Create book cate successfully", HttpStatus.OK);
    }
    @GetMapping("/users/book-categories")
    @Operation(summary = "Get all BookCate by use")
    public ResponseEntity<?> getAllBookCate() {
        return new ResponseEntity<>(bookCateService.getAllBookCate(), HttpStatus.OK);
    }
    @DeleteMapping("/admin/book-categories")
    @Operation(summary = "delete BookCate by use")
    public ResponseEntity<?> deleteBookCate(@RequestParam(name = "bookCateId") Long bookCateId) {
        bookCateService.deleteBookCate(bookCateId);
        return new ResponseEntity<>("Delete book cate successfully", HttpStatus.OK);
    }
    @PutMapping("/admin/book-categories")
    public ResponseEntity<?> updateBookCate(@RequestBody BookCateDTO request) {
        bookCateService.updateCateBook(request);
        return new ResponseEntity<>("update book cate successfully", HttpStatus.OK);
    }
}
