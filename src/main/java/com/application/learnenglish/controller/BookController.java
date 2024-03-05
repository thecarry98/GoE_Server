package com.application.learnenglish.controller;

import com.application.learnenglish.model.dto.BookDTO;
import com.application.learnenglish.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "Book controller")
public class BookController {
    private final BookService bookService;
    @PostMapping("/admin/books")
    @Operation(summary = "add book")
    public ResponseEntity<?> addBook(@RequestBody BookDTO request) {
        bookService.addBook(request);
        return new ResponseEntity<>("Create book successfully", HttpStatus.OK);
    }
    @PutMapping("/admin/books")
    public ResponseEntity<?> updateBook(@RequestBody BookDTO request) {
        bookService.updateBook(request);
        return new ResponseEntity<>("update book successfully", HttpStatus.OK);
    }
    @GetMapping("/users/books")
    public ResponseEntity<?> getAllBook() {
        return new ResponseEntity<>(bookService.getAllBook(), HttpStatus.OK);
    }
    @GetMapping("/books")
    public ResponseEntity<?> BookHomePage() {
        return new ResponseEntity<>(bookService.getHomePage(), HttpStatus.OK);
    }
    @GetMapping("/users/{bookCateId}/books")
    public ResponseEntity<?> getBookByBookCate(@PathVariable(name = "bookCateId") Long bookCateId) {
        return new ResponseEntity<>(bookService.getAllBookByCateBook(bookCateId), HttpStatus.OK);
    }
    @GetMapping("/users/books/{bookId}")
    public ResponseEntity<?> getAllBook(@PathVariable(name = "bookId") Long bookId) {
        return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
    }
    @DeleteMapping("/admin/book")
    @Operation(summary = "delete Book")
    public ResponseEntity<?> deleteBook(@RequestParam(name = "bookId") Long bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>("Delete book successfully", HttpStatus.OK);
    }
}
