package com.application.learnenglish.controller;

import com.application.learnenglish.model.dto.BookDTO;
import com.application.learnenglish.model.dto.LessonDTO;
import com.application.learnenglish.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "lesson controller")
public class LessonController {
    private final LessonService lessonService;
    @PostMapping("/admin/lessons")
    @Operation(summary = "add lesson")
    public ResponseEntity<?> addLesson(@RequestBody LessonDTO request) {
        lessonService.addLesson(request);
        return new ResponseEntity<>("Create lesson successfully", HttpStatus.OK);
    }
    @PutMapping("/admin/lessons")
    public ResponseEntity<?> updateLesson(@RequestBody LessonDTO request) {
        lessonService.updateLesson(request);
        return new ResponseEntity<>("update lesson successfully", HttpStatus.OK);
    }
    @GetMapping("/users/lessons/{lessonId}")
    public ResponseEntity<?> getLessonById(@PathVariable(name = "lessonId") Long lessonId) {
        return new ResponseEntity<>(lessonService.getLessonById(lessonId), HttpStatus.OK);
    }
    @DeleteMapping("/users/lessons/{lessonId}")
    public ResponseEntity<?> deleteLesson(@PathVariable(name = "lessonId") Long lessonId) {
        lessonService.deleteLesson(lessonId);
        return new ResponseEntity<>("delete lesson successfully", HttpStatus.OK);
    }


}
