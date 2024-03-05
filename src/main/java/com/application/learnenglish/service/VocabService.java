package com.application.learnenglish.service;

import com.application.learnenglish.model.dto.MyVocabRequest;
import com.application.learnenglish.model.dto.VocabDTO;

import java.util.List;

public interface VocabService {
    VocabDTO addVocab(VocabDTO vocab, Long lessonId);
    void deleteVocab(Long vocabId);
    String addCateVocab(String name);
    List<String> getCateVocab();
    void addToMyVocab(MyVocabRequest request);
    List<VocabDTO> getVocabByUser(Long userId);
}
