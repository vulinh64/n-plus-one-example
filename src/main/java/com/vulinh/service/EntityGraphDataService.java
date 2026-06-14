package com.vulinh.service;

import com.vulinh.data.dto.AuthorDTO;
import com.vulinh.data.mapper.AuthorMapper;
import com.vulinh.data.repository.UglyAuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityGraphDataService {

  private final UglyAuthorRepository uglyAuthorRepository;

  public Page<AuthorDTO> getAuthorsEntityGraph(Pageable pageable) {
    return uglyAuthorRepository.findAll(pageable).map(AuthorMapper.INSTANCE::toAuthor);
  }
}
