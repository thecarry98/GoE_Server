package com.application.learnenglish.service.implement;

import com.application.learnenglish.model.dto.respone.NewResponse;
import com.application.learnenglish.model.entity.News;
import com.application.learnenglish.repository.NewRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewServiceImpl {
    private final NewRepository newRepo;

    public List<NewResponse> getAllNews() {
        List<News> news = newRepo.findAll();
        if (ObjectUtils.isEmpty(news)) {
            return Collections.emptyList();
        }
        return news.stream()
                .sorted(Comparator.comparing(News::getId))
                .map(news1 -> NewResponse.builder()
                        .id(news1.getId())
                        .title(news1.getTitle())
                        .description(news1.getDescription())
                        .url(news1.getUrl())
                        .urlToImage(news1.getUrlImage())
                        .build())
                .collect(Collectors.toList());
    }
}
