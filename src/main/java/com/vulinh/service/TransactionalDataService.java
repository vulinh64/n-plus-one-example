package com.vulinh.service;

import com.vulinh.data.dto.AuthorDTO;
import com.vulinh.data.mapper.AuthorMapper;
import com.vulinh.data.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionalDataService {

  private final AuthorRepository authorRepository;

  @Transactional(readOnly = true)
  public Page<AuthorDTO> getAuthorsTransactional(Pageable pageable) {
    return authorRepository.findAll(pageable).map(AuthorMapper.INSTANCE::toAuthor);
  }
}
