package com.application.learnenglish.repository;

import com.application.learnenglish.model.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Long> {
    Books findByBookName(String bookName);
    @Query("select b from Books b where b.cateBookId.id=:cateBookId")
    List<Books> findAllByCateBookId(@Param("cateBookId") Long cateBookId);
}
