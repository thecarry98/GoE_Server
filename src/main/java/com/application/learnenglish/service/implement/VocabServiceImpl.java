package com.application.learnenglish.service.implement;

import com.application.learnenglish.exception.ApplicationRuntimeException;
import com.application.learnenglish.model.dto.MyVocabRequest;
import com.application.learnenglish.model.dto.VocabDTO;
import com.application.learnenglish.model.entity.*;
import com.application.learnenglish.repository.*;
import com.application.learnenglish.service.VocabService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VocabServiceImpl implements VocabService {
    private final VocabRepository vocabRepo;
    private final CateVocabRepository cateVocabRepo;
    private final LessonRepository lessonRepo;
    private final UserRepository userRepo;
    private final MyVocabRepository myVocabRepo;

    @Override
    public VocabDTO addVocab(VocabDTO request, Long lessonId) {
        Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Lesson is not exits!");
        });
        CateVocabs cateVocab = cateVocabRepo.findByCateVocabName(request.getCateVocab());
        if (ObjectUtils.isEmpty(cateVocab)) {
            throw new ApplicationRuntimeException("Vocab category is not exits!");
        }
        Vocab vocab = Vocab.builder()
                .enVocab(request.getEnVocab())
                .vnVocab(request.getVnVocab())
                .index(request.getIndex())
                .cateVocab(cateVocab)
                .lessons(lesson)
                .build();
        vocabRepo.save(vocab);
        return VocabDTO.builder()
                .id(vocab.getId())
                .cateVocab(vocab.getCateVocab().getCateVocabName())
                .enVocab(vocab.getEnVocab())
                .vnVocab(vocab.getVnVocab())
                .index(vocab.getIndex())
                .build();
    }

    @Override
    public void deleteVocab(Long vocabId) {
        Vocab vocab = vocabRepo.findById(vocabId).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Vocab not found");
        });
        vocabRepo.delete(vocab);
    }

    @Override
    public String addCateVocab(String name) {
        CateVocabs cateVocabs = CateVocabs.builder().cateVocabName(name).build();
        cateVocabRepo.save(cateVocabs);
        return name;
    }

    @Override
    public List<String> getCateVocab() {
        List<CateVocabs> cateVocabs = cateVocabRepo.findAll();
        if (ObjectUtils.isEmpty(cateVocabs)) {
            return Collections.emptyList();
        }
        return cateVocabs.stream()
                .map(CateVocabs::getCateVocabName)
                .collect(Collectors.toList());
    }

    @Override
    public void addToMyVocab(MyVocabRequest request) {
        User user = userRepo.findById(request.getUserId()).orElseThrow(() -> {
            throw new ApplicationRuntimeException("User not found!");
        });
        Vocab vocab = vocabRepo.findById(request.getVocabId()).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Vocab not found!");
        });
        MyVocab myVocab = MyVocab.builder()
                .user(user)
                .vocab(vocab)
                .build();
        myVocabRepo.save(myVocab);
    }

    @Override
    public List<VocabDTO> getVocabByUser(Long userId) {
        List<MyVocab> myVocabs = myVocabRepo.findByUserId(userId);
        if (ObjectUtils.isEmpty(myVocabs)) {
            return Collections.emptyList();
        }
        List<Vocab> vocabs = new ArrayList<>();
        for (MyVocab myVocab : myVocabs) {
            vocabs.add(myVocab.getVocab());
        }
        return vocabs.stream().map(vocab -> VocabDTO.builder()
                .id(vocab.getId())
                .enVocab(vocab.getEnVocab())
                .vnVocab(vocab.getVnVocab())
                .cateVocab(vocab.getCateVocab().getCateVocabName())
                .build()).collect(Collectors.toList());
    }
}
