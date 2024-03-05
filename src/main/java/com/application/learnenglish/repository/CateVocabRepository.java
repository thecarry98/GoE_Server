package com.application.learnenglish.repository;

import com.application.learnenglish.model.entity.CateVocabs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CateVocabRepository extends JpaRepository<CateVocabs, Long> {
    CateVocabs findByCateVocabName(String name);
}
