package com.application.learnenglish.repository;

import com.application.learnenglish.model.entity.BookCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCateRepository extends JpaRepository<BookCategories, Long> {
    BookCategories findByName(String name);
}
