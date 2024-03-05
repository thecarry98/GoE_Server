package com.application.learnenglish.repository;

import com.application.learnenglish.model.entity.Speakings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeakingRepository extends JpaRepository<Speakings, Long> {
}
