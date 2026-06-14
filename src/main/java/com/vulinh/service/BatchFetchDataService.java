package com.vulinh.service;

import com.vulinh.data.dto.CustomerDTO;
import com.vulinh.data.mapper.CustomerMapper;
import com.vulinh.data.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BatchFetchDataService {

  private final CustomerRepository customerRepository;

  @Transactional(readOnly = true)
  public Page<CustomerDTO> getCustomersBatchFetch(Pageable pageable) {
    return customerRepository.findAll(pageable).map(CustomerMapper.INSTANCE::toCustomerDTO);
  }
}
