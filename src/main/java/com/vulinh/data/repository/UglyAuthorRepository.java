package com.vulinh.data.repository;

import com.vulinh.data.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UglyAuthorRepository extends AuthorRepository {

  @Override
  @EntityGraph(attributePaths = "books")
  @NonNull
  Page<Author> findAll(@NonNull Pageable pageable);
}
