package com.application.learnenglish.repository;

import com.application.learnenglish.model.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewRepository extends JpaRepository<News, Long> {
}
