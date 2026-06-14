package com.vulinh.controller;

import com.vulinh.data.dto.AuthorDTO;
import com.vulinh.service.EntityGraphDataService;
import com.vulinh.service.JoinFetchDataService;
import com.vulinh.service.NoTransactionDataService;
import com.vulinh.service.TransactionalDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
@Slf4j
public class AuthorController {

  private final NoTransactionDataService noTransactionDataService;
  private final TransactionalDataService transactionalDataService;
  private final EntityGraphDataService entityGraphDataService;
  private final JoinFetchDataService joinFetchDataService;

  @GetMapping("/no-transaction")
  public Page<AuthorDTO> getAuthorsLazyException(Pageable pageable) {
    RequestLogger.logRequest("/no-transaction");

    return noTransactionDataService.getAuthorsLazyException(pageable);
  }

  @GetMapping("/transactional")
  public Page<AuthorDTO> getAuthorsTransactional(Pageable pageable) {
    RequestLogger.logRequest("/transactional");

    return transactionalDataService.getAuthorsTransactional(pageable);
  }

  @GetMapping("/entity-graph")
  public Page<AuthorDTO> getAuthorsEntityGraph(Pageable pageable) {
    RequestLogger.logRequest("/entity-graph");

    return entityGraphDataService.getAuthorsEntityGraph(pageable);
  }

  @GetMapping("/join-fetch")
  public Page<AuthorDTO> getAuthorsJoinFetch(Pageable pageable) {
    RequestLogger.logRequest("/join-fetch");

    return joinFetchDataService.getAuthorJoinFetch(pageable);
  }
}
