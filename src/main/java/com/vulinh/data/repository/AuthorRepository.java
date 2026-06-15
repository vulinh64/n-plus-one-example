package com.vulinh.data.repository;

import com.vulinh.data.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends BaseRepository<Author, Long> {

  @Query(value = "select distinct a from Author a left join fetch a.books")
  Page<Author> getAllAuthorJoinFetch(Pageable pageable);
}
