package com.application.learnenglish.repository;

import com.application.learnenglish.model.entity.User;
import com.application.learnenglish.model.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUserName(String username);
    User findByEmail(String email);
    Page<User> findAll(Specification<User> specification, Pageable pageable);
    List<User> findAll();
    List<User> findByRole(Role role);
}
