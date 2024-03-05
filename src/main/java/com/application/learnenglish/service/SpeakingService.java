package com.application.learnenglish.service;

import com.application.learnenglish.model.dto.SpeakingDTO;

import java.util.List;

public interface SpeakingService {
    SpeakingDTO addSpeaking(SpeakingDTO request);
    SpeakingDTO updateSpeaking(SpeakingDTO response);
    List<SpeakingDTO> getAllSpeaking();
    SpeakingDTO getById(Long id);
    void deleteSpeakingById(Long id);

}
