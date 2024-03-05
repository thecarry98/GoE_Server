package com.application.learnenglish.controller;

import com.application.learnenglish.model.dto.MyVocabRequest;
import com.application.learnenglish.model.dto.PostRequest;
import com.application.learnenglish.model.dto.VocabDTO;
import com.application.learnenglish.service.VocabService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "Vocab Controller")
public class VocabController {
    private final VocabService vocabService;

    @PostMapping("/admin/{lessonId}/vocab")
    public ResponseEntity<?> addPost(@RequestBody VocabDTO request,
                                     @PathVariable(name = "lessonId") Long lessonId) {
        return new ResponseEntity<>(vocabService.addVocab(request, lessonId), HttpStatus.OK);
    }
    @PostMapping("/admin/cate-vocab")
    public ResponseEntity<?> addCateVocabs(@RequestParam(name = "name") String name) {
        return new ResponseEntity<>(vocabService.addCateVocab(name), HttpStatus.OK);
    }
    @PostMapping("/users/my-vocab")
    public ResponseEntity<?> addVocabToMyVocab(@RequestBody MyVocabRequest request) {
        vocabService.addToMyVocab(request);
        return new ResponseEntity<>("added vocab to your vocab",HttpStatus.OK);
    }
    @GetMapping("/admin/cate-vocab")
    @Operation(summary = "get cate vocab")
    public ResponseEntity<?> getAllCateVocab() {
        return new ResponseEntity<>(vocabService.getCateVocab(),HttpStatus.OK);
    }
    @GetMapping("/users/my-vocab")
    @Operation(summary = "get my vocab by user")
    public ResponseEntity<?> getMyVocabByUser(@RequestParam(name = "userId") Long userId) {
        return new ResponseEntity<>(vocabService.getVocabByUser(userId),HttpStatus.OK);
    }
    @DeleteMapping("/admin/vocab")
    public ResponseEntity<?> addCateVocabs(@RequestParam(name = "vocabId") Long vocabId) {
        vocabService.deleteVocab(vocabId);
        return new ResponseEntity<>("Delete vocab successfully",HttpStatus.OK);
    }
}
