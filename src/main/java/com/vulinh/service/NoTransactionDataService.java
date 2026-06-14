package com.vulinh.service;

import com.vulinh.data.dto.AuthorDTO;
import com.vulinh.data.mapper.AuthorMapper;
import com.vulinh.data.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoTransactionDataService {

  private final AuthorRepository authorRepository;

  // EXPECT: Lazy exception
  public Page<AuthorDTO> getAuthorsLazyException(Pageable pageable) {
    return authorRepository.findAll(pageable).map(AuthorMapper.INSTANCE::toAuthor);
  }
}
