package com.application.learnenglish.controller;

import com.application.learnenglish.model.dto.SpeakingDTO;
import com.application.learnenglish.service.SpeakingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "Speaking Controller")
public class SpeakingController {
    private final SpeakingService speakingService;

    @PostMapping("/admin/speaking")
    @Operation(summary = "add speaking")
    public ResponseEntity<?> addSpeaking(@RequestBody SpeakingDTO request) {
        return new ResponseEntity<>(speakingService.addSpeaking(request), HttpStatus.OK);
    }

    @PutMapping("/admin/speaking")
    public ResponseEntity<?> updateSpeaking(@RequestBody SpeakingDTO request) {
        return new ResponseEntity<>(speakingService.updateSpeaking(request), HttpStatus.OK);
    }

    @GetMapping("/users/speaking")
    public ResponseEntity<?> getAllSpeakings() {
        return new ResponseEntity<>(speakingService.getAllSpeaking(), HttpStatus.OK);
    }

    @GetMapping("/users/speaking/{speakingId}")
    public ResponseEntity<?> getSpeakingById(@PathVariable(name = "speakingId") Long speakingId) {
        return new ResponseEntity<>(speakingService.getById(speakingId), HttpStatus.OK);
    }

    @DeleteMapping("/admin/speaking")
    public ResponseEntity<?> deleteSpeakingById(@RequestParam(name = "speakingId") Long speakingId) {
        speakingService.deleteSpeakingById(speakingId);
        return new ResponseEntity<>("Delete speaking successfully!", HttpStatus.OK);
    }
}
