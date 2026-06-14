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
public class JoinFetchDataService {

  private final AuthorRepository authorRepository;

  @Transactional(readOnly = true)
  public Page<AuthorDTO> getAuthorJoinFetch(Pageable pageable) {
    return authorRepository.getAllAuthorJoinFetch(pageable).map(AuthorMapper.INSTANCE::toAuthor);
  }
}
