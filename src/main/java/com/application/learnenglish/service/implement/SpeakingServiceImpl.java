package com.application.learnenglish.service.implement;

import com.application.learnenglish.exception.ApplicationRuntimeException;
import com.application.learnenglish.model.dto.SpeakingDTO;
import com.application.learnenglish.model.entity.SpeakingItem;
import com.application.learnenglish.model.entity.Speakings;
import com.application.learnenglish.model.entity.User;
import com.application.learnenglish.repository.SpeakingRepository;
import com.application.learnenglish.service.SpeakingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpeakingServiceImpl implements SpeakingService {
    private final SpeakingRepository speakingRepo;

    @Override
    public SpeakingDTO addSpeaking(SpeakingDTO request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Speakings speaking = Speakings.builder()
                .speakingName(request.getSpeakingName())
                .speakingItem(getSpeakingItem(request.getSpeakingItem()))
                .user(user).build();
        speakingRepo.save(speaking);
        return Speakings.convertToSpeakingDTO(speaking);
    }

    @Override
    public SpeakingDTO updateSpeaking(SpeakingDTO request) {
        Speakings speaking = speakingRepo.findById(request.getId()).orElseThrow(() -> {
                    throw new ApplicationRuntimeException("Speaking not exits");
                }
        );
        speaking.setSpeakingName(speaking.getSpeakingName());
        speaking.setSpeakingItem(getSpeakingItem(request.getSpeakingItem()));
        speakingRepo.save(speaking);
        return Speakings.convertToSpeakingDTO(speaking);
    }

    @Override
    public List<SpeakingDTO> getAllSpeaking() {
        List<Speakings> speakings = speakingRepo.findAll();
        if (ObjectUtils.isEmpty(speakings)) {
            return Collections.emptyList();
        }
        return speakings.stream()
                .sorted(Comparator.comparing(Speakings::getId))
                .map(speakings1 -> Speakings.convertToSpeakingDTO(speakings1))
                .collect(Collectors.toList());
    }

    @Override
    public SpeakingDTO getById(Long id) {
        Speakings speaking = speakingRepo.findById(id).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Speaking not exits!");
        });
        return Speakings.convertToSpeakingDTO(speaking);
    }

    @Override
    public void deleteSpeakingById(Long id) {
        Speakings speaking = speakingRepo.findById(id).orElseThrow(() -> {
            throw new ApplicationRuntimeException("Speaking not exits!");
        });
        speakingRepo.delete(speaking);
    }

    private Set<SpeakingItem> getSpeakingItem(Set<SpeakingItem> speakingItems) {
        if (ObjectUtils.isEmpty(speakingItems)) {
            return Collections.emptySet();
        }
        AtomicInteger index = new AtomicInteger();
        return speakingItems.stream().map(speakingItem -> SpeakingItem.builder()
                .index(index.getAndIncrement())
                .siQuestion(speakingItem.getSiQuestion())
                .siAnswer(speakingItem.getSiAnswer())
                .build()).collect(Collectors.toSet());
    }


}
